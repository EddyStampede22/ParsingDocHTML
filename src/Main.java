import javax.swing.text.html.HTMLEditorKit;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.*;

public class Main {
    public static final Logger LOG = Logger.getLogger( Main.class.getName() );
    public static void main(String[] args) {



        if( args.length == 0 ) {
            LOG.severe("Falto incluir documento HTML");
            System.exit(1);
        }

        FileReader fileReader = null;

        try {
            fileReader = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            LOG.severe("No se puede abrir documento HTML");
            System.exit(2);
        }

        HtmlParsing p  = new HtmlParsing();

        HTMLEditorKit.Parser procesador = p.getParser();
        try {
            procesador.parse( fileReader, new EncontrarPalabra(args[1],args[0]), true);
        } catch (IOException e) {
            LOG.severe("No se puede leer documento HTML");
            System.exit(2);
        }




    }
    }
