import sys
import cv2
import numpy as np
from collections import deque
from PySide6.QtCore import *
from PySide6.QtWidgets import *
from PySide6.QtGui import *

class MainWindow(QMainWindow):
    def __init__(self, parent=None):
        super(MainWindow, self).__init__(parent)

        self.canvas = Canvas()
        self.setCentralWidget(self.canvas)
        self.initUI()
    #ウィンドウ設定
    def initUI(self):
        self.setGeometry(100, 200, 800, 800)
        self.setWindowTitle("PAINTapl")
        #メニューバーの設定
        menubar = self.menuBar()
        openAct = QAction("&画像開く", self)
        saveAct = QAction("&保存", self)
        exitAct = QAction("&終了", self)
        openAct.setShortcut("Ctrl+O")
        openAct.triggered.connect(self.openFile)
        saveAct.setShortcut("Ctrl+S")
        saveAct.triggered.connect(self.saveFile)
        exitAct.triggered.connect(QCoreApplication.instance().quit)
        fileMenu = menubar.addMenu("&ファイル")
        fileMenu.addAction(openAct)
        fileMenu.addAction(saveAct)
        fileMenu.addAction(exitAct)

        #グリッド表示切り替え
        self.cb_image1 = QCheckBox(self)
        self.cb_image1.move(300, 20)
        self.cb_image1.resize(200, 30)
        self.cb_image1.setText("グリッドを表示")
        self.cb_image1.setCheckState(Qt.Checked)
        self.cb_image1.stateChanged.connect(self.gridControl)

        #ボタングループの設定
        self.bg_paint = QButtonGroup()
        self.bg_image1 = QButtonGroup()
        #ペイントツール用のトグルボタン
        self.dt_paint = [
            (0, 0, 0, "Pen", "image/Icon_Pen.png"),
            (0, 1, 1, "Line", "image/Icon_Line.png"),
            (0, 2, 2, "RectLine", "image/Icon_RectLine.png"),
            (0, 3, 3, "RectFill", "image/Icon_RectFill.png"),
            (0, 4, 4, "Lattice", "image/Icon_Lattice.png"),
            (0, 5, 5, "Eracer", "image/Icon_Eracer.png"),
        ]
        for x, y, id, _, iconImg in self.dt_paint:
            qimage = QImage(iconImg)
            qpix = QPixmap.fromImage(qimage)
            icon = QIcon(qpix)
            btn = QPushButton(icon, "", self)
            btn.move(40*x + 60, 40*y + 60)
            btn.resize(40, 40)
            btn.setIconSize(qpix.size())
            btn.setCheckable(True)
            if id == 0:
                btn.setChecked(True)
            self.bg_paint.addButton(btn, id)
        self.bg_paint.setExclusive(True)  # 排他的なボタン処理にする
        self.bg_paint.buttonClicked.connect(self.changedButton)
        #編集ボタン
        self.dt_image1 = [
            (0, 0, 0, "ClearAll", "image/Icon_Clear.png"),
            (1, 0, 1, "Previous", "image/Icon_Previous.png"),
            (2, 0, 2, "Next", "image/Icon_Next.png"),
        ]
        for x, y, id, _, iconImg in self.dt_image1:
            qimage = QImage(iconImg)
            qpix = QPixmap.fromImage(qimage)
            icon = QIcon(qpix)
            btn = QPushButton(icon, "", self)
            btn.move(40*x + self.canvas.offsetx_image1, 40*y + 10)
            btn.resize(40, 40)
            btn.setIconSize(qpix.size())
            self.bg_image1.addButton(btn, id)
        self.bg_image1.buttonClicked.connect(self.pushedButton_image1)

    def changedButton(self):
        id = self.bg_paint.checkedId()
        self.canvas.drawMode = self.dt_paint[id][3]

    def pushedButton_image1(self, obj):
        id = self.bg_image1.id(obj)
        processMode = self.dt_image1[id][3]
        self.pushedButton(processMode, self.canvas.image1, self.canvas.back1, self.canvas.next1)

    def pushedButton(self, processMode, image, queBack, queNext):
        if processMode == "ClearAll":
            queBack.append(self.canvas.resizeImage(image, image.size()))
            if id(image) == id(self.canvas.image1):
                image.fill(255)
                self.update()

        elif processMode == "Previous":
            if queBack:
                back_ = queBack.pop()
                queNext.append(self.canvas.resizeImage(image, image.size()))
                image.swap(back_)
                self.update()
        elif processMode == "Next":
            if queNext:
                next_ = queNext.pop()
                queBack.append(self.canvas.resizeImage(image, image.size()))
                image.swap(next_)
                self.update()


    def gridControl(self):
        if self.cb_image1.checkState() == Qt.Checked:
            self.canvas.gridOn(self.canvas.image1_grid)
        elif self.cb_image1.checkState() == Qt.Unchecked:
            self.canvas.gridOff(self.canvas.image1_grid)
        self.canvas.update()

    #画像を開く
    def openFile(self):
        path = QDir.currentPath()
        fileName, _ = QFileDialog.getOpenFileName(self, "Open File", path)
        if fileName:
            self.canvas.openImage(fileName, self.canvas.image1)

    #画像を保存
    def saveFile(self):
        path = QDir.currentPath()
        fileName, _ = QFileDialog.getSaveFileName(self, "Save as", path)
        if fileName:
            if not fileName.endswith(".png"):
                fileName = fileName + ".png"
            return self.canvas.saveImage(fileName, self.canvas.image1)
        return False

    def DrawLineMode(self):
        self.canvas.drawMode = "Line"

    def keyPressEvent(self, event):
        if event.isAutoRepeat():
            return
        pressed = event.key()
        if pressed == Qt.Key_Shift:
            self.canvas.shiftKey = True

    def keyReleaseEvent(self, event):
        pressed = event.key()
        if pressed == Qt.Key_Shift:
            self.canvas.shiftKey = False


class Canvas(QWidget):
    colorChanged = Signal(QColor)  # シグナルの作成

    def __init__(self, parent=None):
        super(Canvas, self).__init__(parent)

        #self.imgMag = 16                                    #画像の拡大倍率
        #self.canvasSize = 512                               #画像領域の大きさ
        self.imgMag = 16                                    #画像の拡大倍率
        self.canvasSize = 512                               #画像領域の大きさ
        self.imgSize = int(self.canvasSize/self.imgMag)     #画像の実際の画像サイズ
        #self.imgSize = 1080     #画像の実際の画像サイズ
        self.divColor = 17
        self.myPenColor = QColor(0,0,0)
        self.myPenWidth = 1
        self.image1 = QImage()
        self.guide_image1 = QImage()
        self.image1_grid = QImage(self.canvasSize, self.canvasSize, QImage.Format_ARGB32)       #原画像のグリッド
        self.shiftKey = False

        self.check1 = False

        self.back1 = deque(maxlen = 10)
        self.next1 = deque(maxlen = 10)


        #ウィンドウ左上からのキャンバスの位置
        self.offsetx_image1 = 150
        self.offsety_image1 = 60

        #画像1の領域
        self.rect1_place = QRect(self.offsetx_image1, self.offsety_image1, self.canvasSize, self.canvasSize)

        self.setGrid(self.image1_grid)

        self.drawMode = "Pen"
        self.LatticeWidth = 1
        self.LatticeHeight = 1
        self.LatticeSpace = 1

        layout = QHBoxLayout(self)
        layout.setContentsMargins(150, 0, 100, 700)

        self.__button = ColorButton(self)
        self.__button.setMinimumWidth(30)
        self.__button.setMinimumHeight(10)
        self.__button.colorChanged[QColor].connect(self.colorChangedCallback)
        layout.addWidget(self.__button)

        self.__slider = QSlider(Qt.Horizontal, self)
        self.__slider.setRange(0, 255)
        self.__slider.setValue(self.__button.color().value())
        self.__slider.valueChanged[int].connect(self.colorChangedCallback)
        layout.addWidget(self.__slider)



    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            self.myPenColor = self.__button.color()
            self.lastPos = event.position().toPoint()
            #画像1の領域のみで有効にする
            if self.rect1_place.contains(self.lastPos.x(), self.lastPos.y()):
                self.back1.append(self.resizeImage(self.image1, self.image1.size()))
                self.check1 = True

    def mouseMoveEvent(self, event):
        if event.buttons() and Qt.LeftButton:
            if self.check1:
                if self.check1:
                    image = self.image1
                    guide_image = self.guide_image1
                    rect = self.rect1_place
                guide_image.fill(qRgba(255, 255, 255,0))
                if self.drawMode == "Pen":
                    self.drawLine(event.position().toPoint(), image, rect)
                elif self.drawMode == "Line":
                    self.drawLine(event.position().toPoint(), guide_image, rect)
                elif self.drawMode == "RectLine":
                    self.drawRect(event.position().toPoint(), guide_image, rect, False)
                elif self.drawMode == "RectFill":
                    self.drawRect(event.position().toPoint(), guide_image, rect, True)
                elif self.drawMode == "Lattice":
                    self.drawLattice(event.position().toPoint(), guide_image, rect)
                elif self.drawMode == "Eracer":
                    self.drawEracer(event.position().toPoint(),guide_image,rect)

    def mouseReleaseEvent(self, event):
        if event.button() == Qt.LeftButton:
            #self.myPenColor = self.__button.color()
            if self.check1:
                if self.check1:
                    image = self.image1
                    rect = self.rect1_place
                    self.guide_image1.fill(qRgba(255, 255, 255,0))
                if self.drawMode == "Pen" or self.drawMode == "Line":
                    self.drawLine(event.position().toPoint(), image, rect)
                elif self.drawMode == "RectLine":
                    self.drawRect(event.position().toPoint(), image, rect, False)
                elif self.drawMode == "RectFill":
                    self.drawRect(event.position().toPoint(), image, rect, True)
                elif self.drawMode == "Lattice":
                    self.drawLattice(event.position().toPoint(), image, rect)
                elif self.drawMode == "Eracer":
                    self.drawEracer(event.position().toPoint(),image,rect)
            self.check1 = False

    #ペンツール、線ツール
    def drawLine(self, endPos, image, rect):
        painter = QPainter(image)
        if id(image) == id(self.guide_image1):
            painter.setPen(
                QPen(QColor(0,0,255,191), self.myPenWidth, Qt.SolidLine, Qt.RoundCap, Qt.RoundJoin)
            )
        else:
            painter.setPen(
                QPen(self.myPenColor, self.myPenWidth, Qt.SolidLine, Qt.RoundCap, Qt.RoundJoin)
            )
        v1 = (self.lastPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        v2 = (endPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        #Shiftキーを押している時は線を水平・垂直・45°方向に限定する
        if self.shiftKey:
            if abs((v2 - v1).x()) > abs((v2 - v1).y()) * 1.5:
                v2.setY(v1.y())
            elif abs((v2 - v1).y()) > abs((v2 - v1).x()) * 1.5:
                v2.setX(v1.x())
            else:
                if v2.x() > v1.x():
                    if v2.y() > v1.y():                     #右下方向
                        v2.setY(v1.y()+(v2.x()-v1.x()))
                    else:                                   #右上方向
                        v2.setY(v1.y()-(v2.x()-v1.x()))
                else:
                    if v2.y() > v1.y():                     #左下方向
                        v2.setY(v1.y()-(v2.x()-v1.x()))
                    else:                                   #右下方向
                        v2.setY(v1.y()+(v2.x()-v1.x()))
        painter.drawLine(v1, v2 )
        self.update()
        if self.drawMode == "Pen":
            self.lastPos = QPoint(endPos)

    #四角形ツール（fillがTrueで塗りつぶし有り、Falseで塗りつぶし無し）
    def drawRect(self, endPos, image, rect, fill):
        painter = QPainter(image)
        if id(image) == id(self.guide_image1):
            penColor = QColor(0,0,255,191)
        else:
            penColor = self.myPenColor
        painter.setPen(
            QPen(penColor, self.myPenWidth, Qt.SolidLine, Qt.RoundCap, Qt.RoundJoin)
        )
        v1 = (self.lastPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        v2 = (endPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        #Shiftキーを押している時は正方形にする
        if self.shiftKey:
            if v2.x() > v1.x():
                if v2.y() > v1.y():                     #右下方向
                    v2.setY(v1.y()+(v2.x()-v1.x()))
                else:                                   #右上方向
                    v2.setY(v1.y()-(v2.x()-v1.x()))
            else:
                if v2.y() > v1.y():                     #左下方向
                    v2.setY(v1.y()-(v2.x()-v1.x()))
                else:                                   #右舌方向
                    v2.setY(v1.y()+(v2.x()-v1.x()))
        rect = QRect(v1, v2)
        if fill:
            painter.fillRect(rect, penColor)
        else:
            painter.drawRect(v1.x(), v1.y(),rect.width()-1, rect.height()-1)
        self.update()
        if self.drawMode == "Pen":
            self.lastPos = QPoint(endPos)

    #格子ツール
    def drawLattice(self, endPos, image, rect):
        painter = QPainter(image)
        if image == self.guide_image1:
            penColor = QColor(0,0,255,191)
        else:
            penColor = self.myPenColor
        painter.setPen(
            QPen(penColor, self.myPenWidth, Qt.SolidLine, Qt.RoundCap, Qt.RoundJoin)
        )
        v1 = (self.lastPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        v2 = (endPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        width = self.LatticeWidth
        height = self.LatticeHeight
        space = self.LatticeSpace
        y = abs((v2-v1).y()) + 1
        x = abs((v2-v1).x()) + 1
        for j in range(y):
            if (v2 - v1).y() >= 0:
                dj = j
            else:
                dj = -j
            if j % (height + space) < height:
                for i in range(x):
                    if (v2 - v1).x() >= 0:
                        di = i
                    else:
                        di = -i
                    if i % (width + space) < width:
                        painter.drawLine(v1.x() + di, v1.y() + dj, v1.x() + di, v1.y() + dj)
        self.update()

    def drawEracer(self,endPos,image,rect):
        # v1 = (self.lastPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        # v2 = (endPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        # image.setPixelColor(v1,QColor(0,0,0,0))
        # drawEracer(v1, v2 )
        # self.update()
        # if self.drawMode == "Eracer":
        #     self.lastPos = QPoint(endPos)
        #
        # self.update()
        painter = QPainter(image)
        v1 = (self.lastPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        v2 = (endPos-rect.topLeft()-QPoint(self.imgMag, self.imgMag)/2)/self.imgMag
        #Shiftキーを押している時は線を水平・垂直・45°方向に限定する
        if self.shiftKey:
            if abs((v2 - v1).x()) > abs((v2 - v1).y()) * 1.5:
                v2.setY(v1.y())
            elif abs((v2 - v1).y()) > abs((v2 - v1).x()) * 1.5:
                v2.setX(v1.x())
            else:
                if v2.x() > v1.x():
                    if v2.y() > v1.y():                     #右下方向
                        v2.setY(v1.y()+(v2.x()-v1.x()))
                    else:                                   #右上方向
                        v2.setY(v1.y()-(v2.x()-v1.x()))
                else:
                    if v2.y() > v1.y():                     #左下方向
                        v2.setY(v1.y()-(v2.x()-v1.x()))
                    else:                                   #右下方向
                        v2.setY(v1.y()+(v2.x()-v1.x()))
        if v1.x()<v2.x() or v1.y()<v2.y():
            for i in range(v1.y(),v2.y()+1):
                for j in range(v1.x(),v2.x()+1):
                    image.setPixelColor(QPoint(j,i),QColor(0,0,0,0))
        elif v1.x()>v2.x() or v1.y()>v2.y():
            for i in range(v2.y(),v1.y()+1):
                for j in range(v2.x(),v1.x()+1):
                    image.setPixelColor(QPoint(j,i),QColor(0,0,0,0))
        # penColor = QColor(0,0,0,0)
        # painter.setPen(
        #     QPen(penColor, self.myPenWidth, Qt.SolidLine, Qt.RoundCap, Qt.RoundJoin)
        # )
        # painter.drawLine(v1, v2 )
        # image.setPixelColor(v1, QColor(0, 0, 0, 0))

        # painter.drawLine(v1, v2 )
        self.update()
        # if self.drawMode == "Eracer":
        #     self.lastPos = QPoint(endPos)







    #グリッドの描画
    def setGrid(self, image):
        image.fill(qRgba(255, 255, 255,0))
        for i in range(0, self.canvasSize + 1, self.imgMag):
            for j in range(0, self.canvasSize):
                if not i == self.canvasSize:
                    image.setPixelColor(i, j, QColor(193,193,225,255))
                    image.setPixelColor(j, i, QColor(193,193,225,255))
                if not i == 0:
                    image.setPixelColor(i-1, j, QColor(193,193,225,255))
                    image.setPixelColor(j, i-1, QColor(193,193,225,255))
        #中心の線を赤色にする
        for j in range(0, self.canvasSize):
            image.setPixelColor(self.canvasSize/2, j, QColor(255,127,127,255))
            image.setPixelColor(j, self.canvasSize/2, QColor(255,127,127,255))
            image.setPixelColor(self.canvasSize/2-1, j, QColor(255,127,127,255))
            image.setPixelColor(j, self.canvasSize/2-1, QColor(255,127,127,255))

    def paintEvent(self, event):
        #キャンバスの描画
        painter1 = QPainter(self)
        rect1_size = QRect(0, 0, self.imgSize, self.imgSize)
        painter1.drawImage(self.rect1_place, self.image1, rect1_size)
        painter1.drawImage(self.rect1_place, self.guide_image1, rect1_size)

        #グリッドの描画
        rect_grid = QRect(0, 0, self.canvasSize, self.canvasSize)
        painter1_grid = QPainter(self)
        painter1_grid.drawImage(self.rect1_place, self.image1_grid, rect_grid)

    #ウィンドウサイズを固定している場合、ウィンドを開いたときだけ動作する処理
    def resizeEvent(self, event):
        if self.image1.width() < self.width() or self.image1.height() < self.height():
            changeWidth = max(self.width(), self.image1.width())
            changeHeight = max(self.height(), self.image1.height())
            self.image1 = self.resizeImage(self.image1, QSize(changeWidth, changeHeight))
            self.guide_image1 = self.resizeimage_grid(self.guide_image1, QSize(changeWidth, changeHeight))
            self.image1_grid = self.resizeimage_grid(self.image1_grid, QSize(changeWidth, changeHeight))
            self.update()


    def resizeImage(self, image, newSize):
        changeImage = QImage(QSize(self.imgSize, self.imgSize), QImage.Format_ARGB32)
        changeImage.fill(255)
        painter = QPainter(changeImage)
        painter.drawImage(QPoint(0, 0), image)
        return changeImage

    def resizeimage_grid(self, image, newSize):
        changeImage = QImage(newSize, QImage.Format_ARGB32)
        changeImage.fill(qRgba(255, 255, 255,0))
        painter = QPainter(changeImage)
        painter.drawImage(QPoint(0, 0), image)
        return changeImage

    def gridOn(self, image):
        self.setGrid(self.image1_grid)

    def gridOff(self, image):
        image.fill(qRgba(255, 255, 255,0))


    def openImage(self, filename, image):
        if not image.load(filename):
            return False
        self.update()
        return True

    def saveImage(self, filename, image):
        if image.save(filename, "PNG"):
            return True
        else:
            return False

    def colorChangedCallback(self, value):
        sender = self.sender()
        if sender == self.__button:
            color = self.__button.color()
            self.__slider.blockSignals(True)
            self.__slider.setValue(color.value())
            self.__slider.blockSignals(False)

        elif sender == self.__slider:
            hsv = self.__button.hsv()
            self.__button.blockSignals(True)
            self.__button.setHsv(hsv[0], hsv[1], value)
            self.__button.blockSignals(False)

        self.colorChanged.emit(self.__button.color()) #


class ColorButton(QPushButton):
    colorChanged = Signal(QColor)

    def __init__(self, *args, **kwargs):
        super(ColorButton, self).__init__(*args, **kwargs)
        self.setFlat(True)
        self.clicked.connect(self.showDialog)

        self.__color = QColor(0, 0, 0)
        self.__update()

    def showDialog(self):
        color = QColorDialog.getColor(self.__color)
        if not color.isValid():
            return

        self.__color = color
        self.__update()
        self.colorChanged.emit(self.__color)

    def __update(self):
        r, g, b, a = self.__color.getRgb()
        self.setStyleSheet(
            '*{border:none;background:rgb(%s, %s, %s);}' % (r, g, b)
        )

    def color(self):
        return self.__color

    def setColor(self, color):
        self.__color = color
        self.__update()
        self.colorChanged.emit(self.__color)

    def rgba(self):
        return self.__color.getRgb()

    def setRgba(self, r, g, b, a=255):
        self.__color.setRgb(r, g, b, a)
        self.__update()
        self.colorChanged.emit(self.__color)

    def hsv(self):
        return self.__color.getHsv()

    def setHsv(self, h, s, v, a=255):
        self.__color.setHsv(h, s, v, a)
        self.__update()
        self.colorChanged.emit(self.__color)


#QImageをndarrayに変換
def qimage_to_cv(qimage):
    w, h, d = qimage.size().width(), qimage.size().height(), qimage.depth()
    arr = np.array(qimage.bits()).reshape((h, w, d // 8))
    h, w = arr.shape[:2]
    #bgrImg = cv2.cvtColor(arr,cv2.COLOR_RGB2BGR)
    arrImg = cv2.resize(arr, (w, h), interpolation=cv2.INTER_NEAREST)   #正常な画像の配列にするために必要な変換
    return arrImg

#ndarrayをQImageに変換
def cv_to_pixmap(cv_image):
    height, width = cv_image.shape[:2]
    bytesPerLine = cv_image.strides[0]
    image = QImage(cv_image.data, width, height, bytesPerLine, QImage.Format_ARGB32).copy()
    return image


def main():
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.show()
    sys.exit(app.exec())

if __name__ == "__main__":
    main()