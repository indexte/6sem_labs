from tkinter import *

from .display import Display
from ..points.point import Point


class SmoothConvex(Frame):
    touch_started = False
    drag_origin = Point(0, 0)
    drag_after = 10
    drag_calls = 0

    def __init__(self, parent):
        Frame.__init__(self, parent)
        self.parent = parent

        self.parent.title("Bezier approximation")
        self.pack(fill=BOTH, expand=1)

        self.columnconfigure(4, weight=1)
        self.rowconfigure(2, weight=1)

        self.canv = Canvas(self, bg="#fff")
        self.canv.grid(row=2, column=0, columnspan=7,
                       padx=5, pady=5,
                       sticky=E + W + S + N)

        self.canv.bind("<Button 1>", self.on_touch_left)
        self.canv.bind("<Button 3>", self.on_touch_right)

        self.display = Display(self.canv)

        spawn_button = Button(self, text="Clear", command=self.on_clear)
        spawn_button.grid(row=0, column=0, padx=6, pady=6)

        # self.show_points = Checkbutton(self, text="Show Bezier", variable=self.display.show_bezier,
        #                                command=self.on_update_settings)
        # self.show_points.grid(row=0, column=3, padx=6, pady=6)

        # self.on_update_point_counter()

    def on_touch_left(self, event):
        self.touch_started = True
        self.drag_origin = Point(event.x, event.y)

        if self.touch_started:
            self.display.points.add(Point(event.x, event.y))
            self.display.update_hull()
            self.display.update()
            self.touch_started = False
            self.drag_calls = 0

    def on_touch_right(self, event):
        self.display.points.remove_if_touched(event.x, event.y)
        self.display.update_hull()
        self.display.update()

    def on_spawn_button(self):
        try:
            n = int(self.spawn_count.get())
        except ValueError:
            n = 0
        self.display.random_points(50, 50, 800, 500, n)
        self.display.update()

    def on_clear(self):
        self.display.clear()
        self.display.update()

    def on_update_settings(self):
        self.display.update()
