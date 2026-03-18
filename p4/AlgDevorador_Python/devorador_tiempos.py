import json
import time
from coloreado_grafo import realizar_voraz

def medir_tiempos():
    # Pedimos el dato directamente al usuario
    try:
        repeticiones = int(input("Introduce el número de repeticiones (ej. 1000): "))
    except ValueError:
        print("Error: Por favor, introduce un número entero válido.")
        return

    print("\nn\tTiempo(ms)\tRepeticiones")

    n = 4
    while n <= 65536:
        ruta_fichero = f'sols/g{n}.json'
        t_acumulado = 0
        
        try:
            with open(ruta_fichero, 'r') as f:
                mapa = json.load(f)
            grafo = mapa["grafo"]

            for _ in range(repeticiones):
                t1 = time.time() * 1000 
                realizar_voraz(grafo)
                t2 = time.time() * 1000
                t_acumulado += (t2 - t1)
            
            t_total = int(t_acumulado)
            
            print(f"{n}\t{t_total}\t\t{repeticiones}")
            
        except FileNotFoundError:
            print(f"{n}\t[Fichero no encontrado: {ruta_fichero}]")
            
        n *= 2

if __name__ == "__main__":
    medir_tiempos()