
def find_lowest_cost_node(costs):
    low_name = None
    low_cost = infinity
    for node in costs:
        cost = costs[node]
        if cost < low_cost and node not in processed:
            low_cost = cost
            low_name = node
    return low_name

# 图的描述
graph = {}
graph["start"] = {}
graph["start"]["a"] = 6
graph["start"]["b"] = 2

graph["a"] = {}
graph["a"]["fin"] = 1

graph["b"] = {}
graph["b"]["a"] = 3
graph["b"]["fin"] = 5

graph["fin"] = {}
infinity = float("inf")

costs = {}
costs["a"] = 6
costs["b"] = 2
costs["fin"] = infinity

parents = {}
parents["a"] = "start"
parents["b"] = "start"
parents["fin"] = None

processed = []

node = find_lowest_cost_node(costs)
while node is not None:
    cost = costs[node]
    neighbors = graph[node]
    for n in neighbors:
        newCost = cost + neighbors[n]
        if costs[n] > newCost:
            costs[n] = newCost
            parents[n] = node
    processed.append(node)
    node = find_lowest_cost_node(costs)
print(costs)





