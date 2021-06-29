from tkinter import Canvas, W
from typing import List

from com.university.bezier.points.edge import median_vector
from com.university.bezier.points.point import Point


class PointList:
    def __init__(self):
        self.list: List[Point] = []

    def add(self, new_point: Point):
        self.list.append(new_point)

    def remove(self, point_to_remove: Point):
        self.list = [p for p in self.list if point_to_remove.dist(p) > Point.epsilon]

    def remove_if_touched(self, x, y):
        self.list = [p for p in self.list if not p.is_touched(x, y)]

    def exists(self, point: Point) -> bool:
        for p in self.list:
            if point.dist(p) < Point.epsilon:
                return True
        return False

    def draw(self, canvas):
        for p in self.list:
            p.draw(canvas)

    def draw_connected(self, canvas: Canvas):
        if len(self.list) > 0:
            points = []
            for p in self.list:
                points.append(p.x)
                points.append(p.y)

            canvas.create_line(points, width=2, fill="#333")

            i = 0
            # slice the array of all elements except the last one
            connections = self.list[:-1]
            for p in connections:
                offset_weight = 30
                prev_point = connections[i - 1]
                if i + 1 < len(connections):
                    next_point = connections[i + 1]
                else:
                    next_point = connections[0]
                offset = median_vector(Point(prev_point.x - p.x, prev_point.y - p.y).normalize(),
                                       Point(next_point.x - p.x, next_point.y - p.y).normalize()) \
                    .normalize().multiply_by_constant(offset_weight)
                canvas.create_text(p.x + offset.x / 2, p.y + offset.y / 2, anchor=W,
                                   text=str(i), fill='red')
                i += 1

    def sort(self, cmp=lambda point: point.x):
        self.list = sorted(self.list, key=cmp)

    def convex_hull(self):
        hull = PointList()
        if len(self.list) == 0:
            return hull
        tmp = self.list
        left_most = self.left_most_pos()
        tmp[0], tmp[left_most] = tmp[left_most], tmp[0]
        tmp = tmp[:1] + sorted(tmp[1:], key=lambda point: point.polar_angle(tmp[0]))

        def cross_product_orientation(a: Point, b: Point, c: Point):
            return (b.y - a.y) * (c.x - a.x) - (b.x - a.x) * (c.y - a.y)

        for p in tmp:
            while len(hull.list) > 1 and cross_product_orientation(hull.list[-2], hull.list[-1], p) >= 0:
                hull.list.pop()
            hull.list.append(p)

        hull.list.append(hull.list[0])

        return hull

    def left_most_pos(self) -> int:
        pos = 0
        i = 0
        for p in self.list:
            if p.x < self.list[pos].x:
                pos = i
            i += 1
        return pos

    def print(self):
        print(["{" + str(p.x) + " " + str(p.y) + "}" for p in self.list])
