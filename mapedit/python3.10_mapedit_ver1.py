import tkinter
chip=0
map_data=[]
for i in range(18):
    map_data.append([0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0])

def draw_map():
    cvs_bg.delete("BG")
    for y in range(18):
        for x in range(24):
            cvs_bg.create_image(32*x+30,32*y+30,image=img[map_data[y][x]],tag="BG")

def set_map(e):
    x= int(e.x/32)
    y= int(e.y/32)
    if 0 <= x and x <= 23 and 0 <= y and y <= 17:
        map_data[y][x]=chip
        draw_map()

def draw_chip():
    cvs_chip.delete("CHIP")
    for i in range(len(img)):
        cvs_chip.create_image(30,30+i*32,image=img[i],tag="CHIP")
    cvs_chip.create_rectangle(10,10+32*chip,47,47+32*chip,outline="red",width=3,tag="CHIP")

def select_chip(e):
    global chip
    y=int(e.y/32)
    if 0<=y and y < len(img):
        chip=y
        draw_chip()

def put_data():
    c=0
    text.delete("1.0","end")
    for y in range(18):
        for x in range(24):
            text.insert("end",str(map_data[y][x])+",")
            if map_data[y][x]==3:
                c=c+1
        text.insert("end","\n")
    text.insert("end","candy="+str(c))

root =tkinter.Tk()
root.geometry("820x820")
root.title("マップエディタ")
cvs_bg=tkinter.Canvas(width=720,height=540,bg="white")
cvs_bg.place(x=10,y=10)
cvs_bg.bind("<Button-1>",set_map)
cvs_bg.bind("<B1-Motion>",set_map)
cvs_chip=tkinter.Canvas(width=60,height=540,bg="black")
cvs_chip.place(x=740,y=10)
cvs_chip.bind("<Button-1>",select_chip)
text=tkinter.Text(width=55,height=28)
text.place(x=10,y=560)
btn=tkinter.Button(text="データ出力",font=("Times New Roman",16),fg="blue",command=put_data)
btn.place(x=400,y=560)
img=[
    tkinter.PhotoImage(file="image/map1.png"),
    tkinter.PhotoImage(file="image/map2.png")
]
draw_map()
draw_chip()
root.mainloop()