package com.dam.act.tem1_2pag16;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.dam.javabeans.Empleado;

public class GenerarXmlEmpleado {
		static final int TAM_NOM= 10;//definimos la cantidad de caracteres que va a tener cada atributo del String, ecir cada char 2 bytes
		//static final int TAM_REG= 36;  //id(4),nombre(20), departamento(int ocupa 4) y salario(double 8)
		
	public static void main(String[] args) throws IOException {

			
			try {
				RandomAccessFile raf = new RandomAccessFile("empleadosObj.dat", "r");
				//instanciamos el DBF:
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				
				//Crear el parser:
				DocumentBuilder builder = factory.newDocumentBuilder();
				
				//DOMImplementation nos facilita el método createDocument para crear un documento XML
				DOMImplementation implementation = builder.getDOMImplementation();
				
				//Crear un documento vacío indicando el nombre del nodo raíz:
				Document document = implementation.createDocument(null, "empleados",null);
				
				//Asignar la versión del XML:
				document.setXmlVersion("1.0");
				
				raf.seek((0));
				
				char[] arrayNombre = new char[TAM_NOM];
				int depto;
				double sal;
				int id;
				
				while (raf.getFilePointer() < raf.length()) {  
					
				//Crear elementos y añadirlos al nodo raíz:
					Element elemento = document.createElement("empleado");
				
	
					
					document.getDocumentElement().appendChild(elemento);
					//Es hijo de 'elemento' empleado:
					Element elemento2 = document.createElement("id");							
					Text texto = document.createTextNode(String.valueOf(raf.readInt()));
					elemento2.appendChild(texto);
					elemento.appendChild(elemento2);
					elemento2 = document.createElement("nombre");
					for (int i = 0; i < arrayNombre.length; i++) {
						arrayNombre[i]= raf.readChar();
					}
					texto = document.createTextNode(String.valueOf(arrayNombre).trim());
					elemento2.appendChild(texto);
					elemento.appendChild(elemento2);
					elemento2 = document.createElement("dep");
					texto = document.createTextNode(String.valueOf(raf.readInt()));
					elemento2.appendChild(texto);
					elemento.appendChild(elemento2);
					elemento2 = document.createElement("salario");
					texto = document.createTextNode(String.valueOf(raf.readDouble()));
					elemento2.appendChild(texto);
					elemento.appendChild(elemento2);
					
					document.getDocumentElement().appendChild(elemento);
					elemento = document.createElement("empleado");
					
				}
				
				raf.close();
				
				//Una vez hemos generado la estructura, crear la fuente XML a partir del documento:
				Source scr = new DOMSource(document);
				
				//Crear el resultado en el fichero xml:
				Result result = new StreamResult(new File("empleados.xml"));
				
				//Obtenemos un TransformerFactory:
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");          
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");//para indicar que la tabulación son 4 espacios
				transformer.transform(scr,  result);
				
				Result consola = new StreamResult(System.out);
				transformer.transform(scr, consola);
				
				

			} catch (ParserConfigurationException e) {
				
			} catch (TransformerConfigurationException e) {
				// TODO: handle exception
			} catch (TransformerFactoryConfigurationError e) {
				// TODO: handle exception
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				

	}
	
	
}


