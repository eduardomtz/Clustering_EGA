/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mineria;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

/**
 *
 * @author eduardomartinez
 */
public class UI extends JFrame{
    
    public UI()
    {
        this.setLayout(new GridBagLayout());
        
        Label lblnodatos = new Label("NoDatos: ");
        Label label = new Label("BD: ");
        JTextField filename = new JTextField("/Users/eduardomartinez/Documents/mineria/representacion.txt");
        JTextField nodatos = new JTextField();
        nodatos.setText("500");
        
        JTextField funcion = new JTextField("6");
        JTextField individuos = new JTextField("200");
        JTextField enteros = new JTextField("1");
        JTextField decimales = new JTextField("40");
        JTextField variables = new JTextField("6");
        JTextField Pc = new JTextField("0.9");
        JTextField Pm = new JTextField("0.01");
        JTextField generaciones = new JTextField("100");
        JTextField minimiza = new JTextField("0");
        
        Label lblfuncion = new Label("funcion: ");
        Label lblindividuos = new Label("individuos: ");
        Label lblenteros = new Label("enteros: ");
        Label lbldecimales = new Label("decimales: ");
        Label lblvariables = new Label("variables: ");
        Label lblpc = new Label("Pc: ");
        Label lblpm = new Label("Pm: ");
        Label lblgeneraciones = new Label("generaciones: ");
        Label lblminimiza = new Label("[0 Min/1 Max] : ");
        
        /*
        FN=funcion;
	N =individuos;
	E =bits_enteros;
	D =bits_decimales;
	V =variables;
	Pc=porcentaje_cruza;
	Pm=porcentaje_muta;
	G =generaciones;
	MM=minimiza;
        */
        
        JButton openBtn = new JButton("Open BD");
        JButton ejecutarEGA = new JButton("Ejecutar EGA");
        JTextField resultado = new JTextField();
        Label fitness = new Label("fitness: ");
        
        XYSeriesCollection datosSerie = new XYSeriesCollection();
        
        AGF agf = new AGF(2);
        EGA ega = new EGA();
        
        
        JFreeChart chart = ChartFactory.createScatterPlot(
                    "Scatter Plot", // chart title
                    "X", // x axis label
                    "Y", // y axis label
                    datosSerie, // data  ***-----PROBLEM------***
                    PlotOrientation.VERTICAL,
                    true, // include legend
                    true, // tooltips
                    false // urls
                    );
        
        

        // create and display a frame...
        ChartPanel panelChart = new ChartPanel(chart);
        
        
        //leer BD
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //datosSerie.removeAllSeries();
                if(filename.getText().length()>0)
                {
                  agf.LeerDatos(filename.getText(), Integer.parseInt(nodatos.getText()));
                  
                  createDataset(datosSerie, agf.data, "Datos");
                  chart.fireChartChanged();
                }
                else
                {
                JFileChooser openFile = new JFileChooser();
                int rVal = openFile.showOpenDialog(null);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                  filename.setText(openFile.getSelectedFile().getAbsolutePath());
                  agf.LeerDatos(filename.getText(), Integer.parseInt(nodatos.getText()));
                  //createDataset(datosSerie, agf.data, "Datos");
                  //chart.fireChartChanged();
                  }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("");
                  //dir.setText("");
                }
                }
            }
        });
        
        ejecutarEGA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //datosSerie.removeAllSeries();
                if(datosSerie.getSeriesCount()>1)
                    datosSerie.removeSeries(1);
                
                int fn = Integer.parseInt(funcion.getText());
                int n = Integer.parseInt(individuos.getText());
                int e = Integer.parseInt(enteros.getText());
                int d= Integer.parseInt(decimales.getText());
                int v= Integer.parseInt(variables.getText());
                double pc= Double.parseDouble(Pc.getText());
                double pm= Double.parseDouble(Pm.getText());
                int g= Integer.parseInt(generaciones.getText());
                int mm= Integer.parseInt(minimiza.getText());
                
                  ega.setParams(fn, n, e, d, v, pc, pm, g, mm);
                
                  Resultado res = ega.ejecutarAlgoritmoGenetico(agf);
                  
                  resultado.setText(String.valueOf(res.getFitnessSemental()));
                  
                  res.creaArchivo();
                  createDataset(datosSerie, res.getFenotipoSemental(), "Centros");
                  
                  
                  Shape cross = ShapeUtilities.createDiagonalCross(5, 1);
                    XYPlot xyPlot = (XYPlot) chart.getPlot();
                    xyPlot.setDomainCrosshairVisible(true);
                    xyPlot.setRangeCrosshairVisible(true);
                    XYItemRenderer renderer = xyPlot.getRenderer();
                    renderer.setSeriesShape(1, cross);
                    renderer.setSeriesPaint(1, Color.blue);
        
                  chart.fireChartChanged();
                  
            }
        });
        //ejecutar AG
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblnodatos, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(nodatos, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(filename, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(openBtn, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(fitness,gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(resultado,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(ejecutarEGA, gbc);
        
        //-----------------PARAMETROS
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblfuncion, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(funcion, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblindividuos, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(individuos,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblenteros,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(enteros,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lbldecimales,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(decimales,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblvariables,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(variables,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblpc,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(Pc,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblpm,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(Pm,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblgeneraciones,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(generaciones,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(lblminimiza,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(minimiza,gbc);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridheight = 2;
        gbc.gridwidth = 4;
        this.add(panelChart,gbc);
        
        this.setTitle("File Chooser");
        
        this.pack();
    }
    
    private void createDataset(XYSeriesCollection result, double[][] data, String nombreSerie) {

            XYSeries series = new XYSeries(nombreSerie);
            for (int i = 0; i < data.length; i++) {
                double x = data[i][0];
                double y = data[i][1];
                series.add(x, y);
            }
            result.addSeries(series);
        
        //return result;
    }
    
    public static void main(String[] args) throws Exception{
        run(new UI(),700,750);
    }
    
    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }
}
