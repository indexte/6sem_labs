from tkinter import *

from com.university.bezier.displayTkinter.smooth_convex import SmoothConvex

if __name__ == '__main__':
    root = Tk()
    root.geometry("800x800")
    root.resizable(True, True)
    app = SmoothConvex(root)

    root.mainloop()
