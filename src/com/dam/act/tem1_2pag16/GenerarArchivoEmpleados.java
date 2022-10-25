package com.dam.act.tem1_2pag16;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.dam.javabeans.Empleado;

public class GenerarArchivoEmpleados {
	static final int TAM_NOM= 10;//definimos la cantidad de caracteres que va a tener cada atributo del String, ecir cada char 2 bytes
	static final int TAM_REG= 36;  //id(4),nombre(20), departamento(int ocupa 4) y salario(double 8)

	public static void main(String[] args) {
		try (RandomAccessFile raf = new RandomAccessFile("empleadosObj.dat", "rw");){
			Empleado[] empleados = { new Empleado(1,"Alberto", 10, 2000.0),
					 new Empleado(2,"Guillermo", 20, 1500.50),
					 new Empleado(3,"Alejandro", 30, 3000.0),
					 new Empleado(4,"Ana", 20, 2300.60),
					 new Empleado(5,"Patricia", 10, 1900.10),
					 
			};
			
			escribirFichero(raf, empleados);
			lecturaFichero(raf);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void lecturaFichero(RandomAccessFile raf) throws IOException {
		raf.seek(0);
		
		char[] arrayNombre = new char[TAM_NOM];
		int depto;
		double sal;
		int id;
		
		while (raf.getFilePointer() < raf.length()) {
			id= raf.readInt();
			for (int i = 0; i < arrayNombre.length; i++) {
				arrayNombre[i]= raf.readChar();
			}
			
			String nombre= new String(arrayNombre);
			depto= raf.readInt();
			sal= raf.readDouble();
		}
		
	}

	private static void escribirFichero(RandomAccessFile raf, Empleado[] empleados) throws IOException {
		//escritura
				StringBuffer sb;
				 //variable contador para definir el identificador
				raf.seek(raf.length()); //estamos posicionando el puntero al final del fichero para que no machaque y añada
				for (int i = 0; i < empleados.length; i++) {
					
					raf.writeInt(empleados[i].getId());
					
					//escribimos el nombre
					sb = new StringBuffer(empleados[i].getNombre());
					sb.setLength(TAM_NOM);
					raf.writeChars(sb.toString());
					
					//escribimos departamento
					raf.writeInt(empleados[i].getDepartamento());
					
					//escribimos el salario
					raf.writeDouble(empleados[i].getSalario());
				}
		
	}

}
