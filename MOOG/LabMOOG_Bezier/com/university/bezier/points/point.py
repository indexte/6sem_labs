import math
from math import sqrt
from tkinter import Canvas


class Point:
    epsilon = 0.0001
    diam = 8.
    outline_color = ''

    def __init__(self, x: float, y: float):
        self.x: float = x
        self.y: float = y

    def __eq__(self, other):
        if isinstance(other, Point):
            return self.dist(point_b=other) < self.epsilon
        return False

    def draw(self, canvas: Canvas):
        canvas.create_oval(self.x - self.diam / 2, self.y - self.diam / 2, self.x + self.diam / 2,
                           self.y + self.diam / 2, fill="#777",
                           width=2, outline=self.outline_color)

    def dist(self, point_b):
        return sqrt((self.x - point_b.x) ** 2 + (self.y - point_b.y) ** 2)

    def is_touched(self, x, y):
        return self.dist(Point(x, y)) < 10

    def polar_angle(self, origin):
        dx = self.x - origin.x
        dy = self.y - origin.y
        th = math.atan2(dy, dx)
        return th

    def normalize(self):
        vector_len = math.sqrt(self.x ** 2 + self.y ** 2)
        if vector_len == 0:
            return Point(0, 0)
        return Point(self.x / vector_len, self.y / vector_len)

    def multiply_by_constant(self, coefficient: float):
        return Point(self.x * coefficient, self.y * coefficient)
