import json

from auxiliar import dibujar_mapa_coloreado, generar_mapa_grafo

def realizar_voraz(grafo:dict):
    colores = ["red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime"]
    mapa_coloreado = {}

    for node in grafo.keys():
        vecinos = grafo[node]

        colores_vecino = get_colores_vecino(mapa_coloreado, vecinos)

        for color in colores:
            if color not in colores_vecino:
                mapa_coloreado[node] = color
                break
            
    return mapa_coloreado


def get_colores_vecino(mapa_coloreado, vecinos):
    #El set() es similar al HashSet en Java
    colores_vecino = set()

    for vecino in vecinos:
        #Hay que convertirlo a string, me dí cuenta ejecuntandolo que sale 
        #porque las claves del dict son str pero del json vienen int
        vecino_str = str(vecino)

        if vecino_str in mapa_coloreado:
            colores_vecino.add(mapa_coloreado[vecino_str])

    return colores_vecino

if __name__ == "__main__":
    n = 10000
    mapa = generar_mapa_grafo(n)
    solucion = realizar_voraz(mapa["grafo"])

    if solucion:
        print("Solución encontrada:", solucion)
        dibujar_mapa_coloreado(mapa, solucion)
        with open('sols/solucion.json', 'w') as f:
            json.dump(solucion, f)
            f.close()
    else:
        print("No se encontró solución.")


