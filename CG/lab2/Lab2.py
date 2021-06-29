import matplotlib.pyplot as plt
import csv
import math


class Vertex:
    def __init__(self, x, y, name="", index=0):
        self.x = x
        self.y = y
        self.name = name
        self.index = index

    def __str__(self):
        return "({},{})".format(self.x, self.y)

    def __eq__(self, other):
        return (self.y, self.x) == (other.y, other.x)

    def __ne__(self, other):
        return (self.y, self.x) != (other.y, other.x)

    def __lt__(self, other):
        return (self.y, self.x) < (other.y, other.x)

    def __le__(self, other):
        return (self.y, self.x) <= (other.y, other.x)

    def __gt__(self, other):
        return (self.y, self.x) > (other.y, other.x)

    def __ge__(self, other):
        return (self.y, self.x) >= (other.y, other.x)


class Edge:
    def __init__(self, begin, end):
        if begin < end:
            self.begin = begin
            self.end = end
        else:
            self.begin = end
            self.end = begin
        self.weight = 1
        self.rotation = math.atan2(self.end.y - self.begin.y, self.end.x - self.begin.x)

    def __str__(self):
        return "{}->{}".format(self.begin, self.end)

    def str_name(self):
        return "{}->{}".format(self.begin.name, self.end.name)

    def str_weight_name(self):
        return "{}-{}-{}".format(self.begin.name, self.weight, self.end.name)


def read_vertices(file_name):
    with open(file_name, "r") as file:
        reader = csv.reader(file)
        result = []
        for row in reader:
            result.append(Vertex(int(row[0]), int(row[1])))
        return result


def read_edges(file_name, vertices):
    with open(file_name, "r") as file:
        reader = csv.reader(file)
        result = []

        for row in reader:
            result.append(Edge(vertices[int(row[0])], vertices[int(row[1])]))

        return result


def print_vertices(vertices):
    print("Vertices:\t", end="")
    for vertex in vertices:
        print(vertex, end=" ")
    print()


def print_edges(edges):
    print("Edges:\t", end="")
    for edge in edges:
        print(edge, end=" ")
    print()


def print_edges_name(edges):
    print("Edges:\t", end="")
    for edge in edges:
        print(edge.str_name(), end=" ")
    print()


def print_edges_weighted(edges):
    print("Edges:\t", end="")
    for edge in edges:
        print(edge.str_weight_name(), end=" ")
    print()


def print_chain(chain):
    print("Chain:\t", end="")
    length = len(chain)
    for i in range(length - 1):
        print(chain[i].name + "--", end="")
    print(chain[length - 1].name)


def print_chains(chains):
    for chain in chains:
        print_chain(chain)


def draw_edge(edge):
    plt.plot([edge.begin.x, edge.end.x], [edge.begin.y, edge.end.y], color="black",
             marker="o", ms="20", mfc="white")


def draw_edges(edges):
    for edge in edges:
        draw_edge(edge)


def draw_vertices(vertices):
    for i in range(len(vertices)):
        plt.annotate(text=vertices[i].name, xy=(vertices[i].x, vertices[i].y), xytext=(0, 0),
                     textcoords="offset points", ha="center", va="center",
                     color="black", weight="heavy")


def draw_find_point(point):
    plt.plot(point.x, point.y, color="black", marker="o", ms="20", mfc="black")
    plt.annotate(text=point.name, xy=(point.x, point.y), xytext=(0, 0),
                 textcoords="offset points", ha="center", va="center",
                 color="white", weight="heavy")


def sum_weight(array):
    result = 0
    for edge in array:
        result += edge.weight
    return result


def create_chain(chain, out_edges, cur_chain, cur_vertex=0):
    for edge in out_edges[cur_vertex]:
        temp = cur_chain - edge.weight
        if temp >= 0:
            cur_chain = temp
        else:
            chain.append(edge.begin)
            create_chain(chain, out_edges, cur_chain, edge.end.index)
            return


def create_chains(chains, out_edges, num_chains, last_vertex):
    for i in range(num_chains):
        create_chain(chains[i], out_edges, i)
        chains[i].append(last_vertex)


def check_chain(chain, point):
    # Binary search in vertices
    down_vertex, up_vertex = chain[0], chain[len(chain) - 1]
    l, r = 0, len(chain) - 1
    while l <= r:
        m = (l + r) // 2
        if chain[m].y < point.y:
            l = m + 1
            down_vertex = chain[m]
        else:
            r = m - 1
            up_vertex = chain[m]
    angel_edge = math.atan2(up_vertex.y - down_vertex.y, up_vertex.x - down_vertex.x)
    angle_point = math.atan2(point.y - down_vertex.y, point.x - down_vertex.x)
    if angel_edge == angle_point:
        # point on edge
        return 0
    elif angel_edge > angle_point:
        # point on right side
        return 1
    else:
        # point on left side
        return -1


def localize_point(chains, point):
    # Check for borders
    if point.y < chains[0][0].y or \
            point.y > chains[0][len(chains[0]) - 1].y:
        return None

    # Binary search in chains
    l, r = 0, len(chains) - 1
    left_chain, right_chain = l, r
    while l <= r:
        m = (l + r) // 2
        check = check_chain(chains[m], point)
        if check == 0:
            return m, m
        if check == 1:
            l = m + 1
            left_chain = m
        if check == -1:
            r = m - 1
            right_chain = m
    if left_chain == right_chain:
        # Check outside of graph
        return None
    else:
        return left_chain, right_chain


########### Main ###########
def main():
    # Read data from files
    vertices = read_vertices("vertices.txt")
    edges = read_edges("edges.txt", vertices)
    find_point = Vertex(11, 4, name="Z")

    # Print read data
    print_vertices(vertices)
    print_edges(edges)

    ### Pre-processing ###
    vertices.sort(key=lambda vertex: (vertex.y, vertex.x))  # Sorting vertices by y then by x
    n = len(vertices)
    for i in range(n):
        vertices[i].name = chr(65 + i)
        vertices[i].index = i

    in_edges = [[] for _ in range(n)]
    out_edges = [[] for _ in range(n)]
    for edge in edges:
        if edge.begin < edge.end:
            out_edges[vertices.index(edge.begin)].append(edge)
            in_edges[vertices.index(edge.end)].append(edge)
        else:
            in_edges[vertices.index(edge.begin)].append(edge)
            out_edges[vertices.index(edge.end)].append(edge)
    for i in range(n):
        in_edges[i].sort(key=lambda e: e.rotation, reverse=False)
        out_edges[i].sort(key=lambda e: e.rotation, reverse=True)

    # Balancing
    for i in range(1, n - 1):
        w_in = sum_weight(in_edges[i])
        w_out = sum_weight(out_edges[i])
        if w_in > w_out:
            out_edges[i][0].weight += w_in - w_out
    for i in range(n - 2, 0, -1):
        w_in = sum_weight(in_edges[i])
        w_out = sum_weight(out_edges[i])
        if w_out > w_in:
            in_edges[i][0].weight += w_out - w_in

    print()
    for i in range(n):
        print(vertices[i].name + "_in\t", end=" ")
        print_edges_weighted(in_edges[i])

        print(vertices[i].name + "_out\t", end=" ")
        print_edges_weighted(out_edges[i])
        print()

    # Creating chains
    num_chains = sum_weight(out_edges[0])
    chains = [[] for _ in range(num_chains)]

    create_chains(chains, out_edges, num_chains, vertices[n - 1])
    print("All chains:")
    print_chains(chains)

    ### Localizing ###
    result = localize_point(chains, find_point)
    print("\n\nLocalizing")
    if result is None:
        print("\nPoint is outside of graph")
    else:
        if result[0] == result[1]:
            print("\nPoint is on chain:")
            print_chain(chains[result[0]])
        else:
            print("\nPoint is between chains:")
            print_chain(chains[result[0]])
            print_chain(chains[result[1]])

    ### Drawing ###
    draw_edges(edges)
    draw_vertices(vertices)
    draw_find_point(find_point)
    plt.show()


if __name__ == '__main__':
    main()

# Z(11;4) - find
