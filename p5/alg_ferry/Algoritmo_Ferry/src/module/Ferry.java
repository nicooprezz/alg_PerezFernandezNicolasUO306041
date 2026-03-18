package module;

import java.util.List;

public class Ferry {
    
    private int boatLength; //longitud de los carriles del barco
    private List<Integer> vehicles;  //lista de veihculos
    private boolean[][] dp; //matriz con las posibles soluciones
    private int[] sumatorio;    //suma acumulada de las longitudes de los vehiculos
    
    public Ferry(int boatLength, List<Integer> vehicles){
        this.boatLength = boatLength;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];
    
        this.sumatorio = new int[vehicles.size() + 1];

        this.sumatorio[0] = 0;
        calcularSumatorio();
    }

    private void calcularSumatorio() {
        for (int i = 0; i <= vehicles.size(); i++) {
            this.sumatorio[i] += sumatorio[i - 1] + vehicles.get(i - 1);
        }
    }

    public void run(){
        //Ponemos la primera posicion a true siempre y el resto de la fila sera false
        dp[0][0] = true;

        for (int i = 1; i <= vehicles.size(); i++) {
            for (int l = boatLength; l >= 0 ; l--) {
                if(!dp[i-1][l]){
                    continue;
                }

                //meter coche en babor
                if(l + vehicles.get(i-1) <= boatLength){
                    dp[i][l + vehicles.get(i-1)] = true;
                }
                //meter coche en estribor
                if(sumatorio[i] - l <= boatLength){
                    dp[i][l] = true;
                }
            }
        }

        
    }

    public void printData(){
        System.out.println("Longitud de los carriles:");
    }
}