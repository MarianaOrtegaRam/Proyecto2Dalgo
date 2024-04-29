import heapq

def dijkstra(graph, start, end):
    # Creamos un diccionario para almacenar el costo mínimo para llegar a cada nodo desde el nodo de inicio
    # y otro diccionario para almacenar el nodo previo en el camino óptimo hacia cada nodo
    distances = {node: float('inf') for node in graph}
    previous_nodes = {node: None for node in graph}
    
    # El costo para llegar al nodo de inicio es 0
    distances[start] = 0
    
    # Creamos una cola de prioridad (heap) para almacenar los nodos según su costo actual
    priority_queue = [(0, start)]
    
    while priority_queue:
        current_distance, current_node = heapq.heappop(priority_queue)
        
        # Si hemos encontrado un camino más corto a este nodo, actualizamos su costo mínimo
        if current_distance > distances[current_node]:
            continue
        
        # Recorremos los vecinos del nodo actual
        for neighbor, weight in graph[current_node].items():
            distance = current_distance + weight
            # Si encontramos un camino más corto hacia el vecino, actualizamos su costo mínimo y nodo previo
            if distance < distances[neighbor]:
                distances[neighbor] = distance
                previous_nodes[neighbor] = current_node
                heapq.heappush(priority_queue, (distance, neighbor))
    
    # Reconstruimos el camino óptimo desde el nodo de inicio al nodo de destino
    path = []
    current_node = end
    while current_node is not None:
        path.append(current_node)
        current_node = previous_nodes[current_node]
    path.reverse()
    
    return distances[end], path

# Ejemplo de uso
graph = {
    'A': {'B': 2, 'C': 4},
    'B': {'A': 2, 'C': 1, 'D': 7},
    'C': {'A': 4, 'B': 1, 'D': 3},
    'D': {'B': 7, 'C': 3}
}

start_node = 'A'
end_node = 'D'
cost, path = dijkstra(graph, start_node, end_node)
print(f"El costo mínimo desde {start_node} hasta {end_node} es {cost}")
print(f"El camino óptimo es: {' -> '.join(path)}")
