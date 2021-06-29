import tkinter as tk


class Point:
    def __init__(self, x=0.0, y=0.0, check=0):
        self.x = x
        self.y = y
        self.check = check

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __repr__(self):
        return "(" + self.x.__str__() + " ; " + self.y.__str__() + ")"


class Interval:
    def __init__(self, begin, end):
        self.begin = begin
        self.end = end

    def __repr__(self):
        return self.begin.__str__() + " --> " + self.end.__str__()


class Node:
    def __init__(self, interval, left_son, right_son, in_intervals, type, index_point):
        self.interval = interval
        self.left_son = left_son
        self.right_son = right_son
        self.in_intervals = in_intervals
        self.type = type
        self.index_point = index_point

    def __repr__(self):
        return "{" + self.interval.__str__() + " l:" + self.left_son.__str__() + " r:" + self.right_son.__str__() + self.in_intervals.__str__() + "}"


def read_points(file_name):
    points_list = []
    input_array = open(file_name).read().split()

    i = 0
    while i < len(input_array):
        points_list.append(Point(int(input_array[i]), int(input_array[i + 1])))
        i += 2
    return points_list


def insert(root, i):
    if root is None:
        return Node(i, None, None, 1, None, 1)

    b = root.interval.begin
    e = root.interval.end

    if i.begin.x <= b.x and i.end.x >= e.x:
        root.in_intervals.append(i)
    if i.begin.x <= (e.x + b.x) / 2:
        root.left_son = insert(root.left_son, i)
    if i.end.x > (e.x + b.x) / 2:
        root.right_son = insert(root.right_son, i)
    return root


def make_intervals(array):
    result = []
    size = len(array)
    for i in range(size - 1):
        new_interval = Interval(array[i], array[i + 1])
        result.append(new_interval)
    return result


COUNT = [10]


def print2DUtil(root, space):
    # Base case
    if (root == None):
        return

    # Increase distance between levels
    space += COUNT[0]

    # Process right child first
    print2DUtil(root.right_son, space)

    # Print current node after space
    # count
    print()
    for i in range(COUNT[0], space):
        print(end=" ")
    if len(root.in_intervals) > 0:
        print(root.in_intervals[root.index_point])

    # Process left child
    print2DUtil(root.left_son, space)


def recursive_tree(parent_tree):
    if len(parent_tree.in_intervals) < 1:
        return
    if parent_tree.type == 1:
        curr_points_up = parent_tree.in_intervals[0:parent_tree.index_point]
        curr_points_up = sorted(curr_points_up, key=lambda point: point.x)
        new_index_up = int(len(curr_points_up) / 2)
        point_end_up = Point(parent_tree.interval.end.x, parent_tree.in_intervals[parent_tree.index_point].y)
        curr_interval_up = Interval(parent_tree.interval.begin, point_end_up)
        parent_tree.left_son = Node(curr_interval_up, None, None, curr_points_up, 0, new_index_up)

        curr_points_down = parent_tree.in_intervals[parent_tree.index_point + 1:len(parent_tree.in_intervals)]
        curr_points_down = sorted(curr_points_down, key=lambda point: point.x)
        new_index_down = int(len(curr_points_down) / 2)
        point_start_down = Point(parent_tree.interval.begin.x, parent_tree.in_intervals[parent_tree.index_point].y)
        curr_interval_down = Interval(point_start_down, parent_tree.interval.end)
        parent_tree.right_son = Node(curr_interval_down, None, None, curr_points_down, 0, new_index_down)
        canvas.create_line(parent_tree.interval.begin.x,
                           parent_tree.in_intervals[parent_tree.index_point].y,
                           parent_tree.interval.end.x,
                           parent_tree.in_intervals[parent_tree.index_point].y, fill="green")
        recursive_tree(parent_tree.left_son)
        recursive_tree(parent_tree.right_son)
    if parent_tree.type == 0:
        curr_points_left = parent_tree.in_intervals[0:parent_tree.index_point]
        curr_points_left = sorted(curr_points_left, key=lambda point: point.y)
        new_index_left = int(len(curr_points_left) / 2)

        point_end_left = Point(parent_tree.in_intervals[parent_tree.index_point].x, parent_tree.interval.end.y)
        curr_interval_left = Interval(parent_tree.interval.begin, point_end_left)
        parent_tree.left_son = Node(curr_interval_left, None, None, curr_points_left, 1, new_index_left)

        curr_points_right = parent_tree.in_intervals[parent_tree.index_point + 1:len(parent_tree.in_intervals)]
        curr_points_right = sorted(curr_points_right, key=lambda point: point.y)
        new_index_right = int(len(curr_points_right) / 2)

        point_start_right = Point(parent_tree.in_intervals[parent_tree.index_point].x, parent_tree.interval.begin.y)
        curr_interval_right = Interval(point_start_right, parent_tree.interval.end)
        parent_tree.right_son = Node(curr_interval_right, None, None, curr_points_right, 1, new_index_right)

        canvas.create_line(parent_tree.in_intervals[parent_tree.index_point].x,
                           parent_tree.interval.begin.y,
                           parent_tree.in_intervals[parent_tree.index_point].x,
                           parent_tree.interval.end.y, fill="red")
        recursive_tree(parent_tree.left_son)
        recursive_tree(parent_tree.right_son)


region = []
Result = []
tree_root = None


def press():
    find(tree_root, region)
    print(Result)


def find(node, D):
    if len(node.in_intervals) == 0:
        return
    if node.type == 0:
        l = region[0].x
        r = region[1].x
        M = node.in_intervals[node.index_point].x
    else:
        l = region[0].y
        r = region[1].y
        M = node.in_intervals[node.index_point].y
    if l <= M <= r:
        if D[0].x <= node.in_intervals[node.index_point].x <= D[1].x and D[0].y <= node.in_intervals[
            node.index_point].y <= D[1].y:
            Result.append(node.in_intervals[node.index_point])
            print(88)
    if node.left_son is not None:
        if l < M:
            find(node.left_son, region)
    if node.right_son is not None:
        if l < M:
            find(node.right_son, region)


def check_click(event):
    x = event.x
    y = event.y
    region.append(Point(x, y))
    canvas.create_oval(x, y, x, y, fill="black")
    if len(region) == 2:
        canvas.create_rectangle(region[0].x, region[0].y, region[1].x, region[1].y)
        return


if __name__ == "__main__":
    root = tk.Tk()
    canvas = tk.Canvas(root)
    canvas.config(width=400, height=400)
    canvas.pack(side=tk.LEFT)
    canvas.bind("<Button-1>", check_click)
    points = read_points("points.txt")
    # region = read_points("region.txt")
    button = tk.Button(root, text="Find me", command=press)
    button.pack()
    points_y = sorted(points, key=lambda point: point.y)
    points_x = sorted(points_y, key=lambda point: point.x)

    for v in points:
        canvas.create_oval(v.x, v.y, v.x + 3, v.y + 3, fill="black")
        str = v.x.__str__() + " " + v.y.__str__()
        canvas.create_text(v.x, v.y, text=str)

    n = len(points)
    point_start = Point(points_x[0].x, points_y[0].y)
    point_end = Point(points_x[n - 1].x, points_y[n - 1].y)
    canvas.create_oval(point_start.x, point_start.y, point_start.x + 3, point_start.y + 3, fill="black")
    canvas.create_oval(point_end.x, point_end.y, point_end.x + 3, point_end.y + 3, fill="black")
    root_Interval = Interval(Point(points_x[0].x, points_y[0].y), Point(points_x[n - 1].x, points_y[n - 1].y))
    tree_root = Node(root_Interval, None, None, points_x, 0, int(n / 2))
    recursive_tree(tree_root)

    # find(tree_root, region)

    print2DUtil(tree_root, 0)
    root.mainloop()
