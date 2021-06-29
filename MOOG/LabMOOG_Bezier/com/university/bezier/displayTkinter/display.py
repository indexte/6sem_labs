import random
from tkinter import BooleanVar, Canvas

from ..bezier_apr import draw_bezier
from ..points.point import Point
from ..points.all_points import PointList


class Display:
    def __init__(self, canvas: Canvas):
        self.canvas: Canvas = canvas
        self.points: PointList = PointList()
        self.hull: PointList = PointList()
        self.closeness: float = 3.
        self.show_bezier: BooleanVar = BooleanVar()
        self.show_bezier.set(True)

    def update_hull(self):
        self.hull = self.points.convex_hull()

    def random_points(self, x0, y0, x1, y1, point_count: int):
        for i in range(0, point_count):
            x = random.uniform(x0, x1)
            y = random.uniform(y0, y1)
            self.points.add(Point(x, y))

        self.update_hull()

    def update(self):
        self.canvas.delete("all")
        self.hull.draw_connected(self.canvas)
        if self.show_bezier.get():
            draw_bezier(self.hull, self.canvas, self.closeness)
        self.points.draw(self.canvas)

    def clear(self):
        self.points = PointList()
        self.hull = PointList()
