import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.util.logging.*;

public class EncontrarPalabra extends HTMLEditorKit.ParserCallback {
    public static final Logger LOG = Logger.getLogger( EncontrarPalabra.class.getName() );

    private boolean inParagraph;
    String palabra;
    int posicionpalabra;
    String html;
    Handler consoleHandler = new ConsoleHandler();
    Handler fileHandler = new FileHandler("custom_"+palabra+".log", 2000, 5);

    public EncontrarPalabra(String palabra,String html) throws IOException {
        inParagraph = false;
        this.palabra = palabra;
        this.html = html;
        posicionpalabra = 0;

    }

    @Override
    public void handleText(char[] data, int pos) {
        if( inParagraph ) {
            String texto = new String(data);
            for(String s:texto.split("[ ,.'\n]")) {
                posicionpalabra+=s.length();
                if (posicionpalabra>texto.length()) {
                    posicionpalabra=0;
                }else if(palabra.equalsIgnoreCase(s)){
                    Handler consoleHandler = new ConsoleHandler();
                    consoleHandler.setFormatter( new SimpleLogFormatter() );
                    consoleHandler.setLevel( Level.FINE);
                    LOG.addHandler( consoleHandler );
                    LOG.setLevel(Level.FINE);
                    LOG.addHandler( new MyHandler() ) ;
                    fileHandler.setFormatter(new MyFormatter());
                    fileHandler.setFilter(new MyFilter());
                    LOG.addHandler(fileHandler);
                    LOG.log(Level.INFO, "Archivo HTML " + html + " Posicion: "+ (posicionpalabra+pos+1)  );
                    System.out.println("la palabra "+palabra+" se encuentra en la posicion "+(posicionpalabra+pos+1));
                }
            }
        }
    }
    @Override
    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if( t == HTML.Tag.HTML) {
            inParagraph = true;
            LOG.setUseParentHandlers(false);
            System.out.println("Loger name: " + LOG.getName());
        }
    }
    @Override
    public void handleEndTag(HTML.Tag t, int pos) {
        if( t == HTML.Tag.BODY ) {
            inParagraph = false;
        }
    }
}
