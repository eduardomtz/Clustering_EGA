/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mineria;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 *
 * @author eduardomartinez
 */
public class Resultado {
    private double[][] fenotipoSemental;
    private double fitnessSemental;
    
    public double[][] getFenotipoSemental()
    {
        return this.fenotipoSemental;
    }
    
    public void setFenotipoSemental(double[] fenotipo, int totalVariables, int novars)
    {
        //this.fenotipoSemental = fenotipo;ç
        int noclusters = totalVariables/novars;
        fenotipoSemental = new double[noclusters][novars];
        //para cada dato multiplicar por cada uno de los centros
        for(int j=0;j<noclusters;j++)
        {
          int idxinicio = j*novars;
          for(int k=0;k<novars;k++)
          {
                  fenotipoSemental[j][k] = fenotipo[idxinicio+k];
          }
        }
    }
    
    public double getFitnessSemental()
    {
        return this.fitnessSemental;
    }
    
    public void setFitnessSemental(double fitness)
    {
        this.fitnessSemental = fitness;
    }
    
    public void creaArchivo()
    {
        try
        {
            PrintStream Fps=new PrintStream(new FileOutputStream(new File("Centros.txt")));
            for(int i=0;i<fenotipoSemental.length;i++)
            {
                Fps.println(i);
                for(int j=0;j<fenotipoSemental[i].length;j++)
                {
                    Fps.print(fenotipoSemental[i][j] + "\t");
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("Excepcion al crear archivo de centros.");
        }
    }
}
