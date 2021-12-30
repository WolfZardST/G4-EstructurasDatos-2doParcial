
package archivos;

import com.grupo4.proyecto.App;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.stage.FileChooser;

public interface SelectorArchivos {
    
    private static FileChooser getFileChooser() {
        
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        
        String rutaApp = Paths.get(".").toAbsolutePath().normalize().toString();
        File carpetaSaves = new File(rutaApp + "/saves");
        
        if (! carpetaSaves.exists()){ carpetaSaves.mkdir(); }
        
        fileChooser.setInitialDirectory(carpetaSaves);
        
        return fileChooser;
    }
    
    public static File cargarArchivoPartida() {
        
        FileChooser fileChooser = getFileChooser();
        fileChooser.setTitle("Cargar Partida");
        
        return fileChooser.showOpenDialog(App.scene.getWindow());
    }
    
    public static File guardarArchivoPartida() {
        
        FileChooser fileChooser = getFileChooser();
        fileChooser.setTitle("Guardar Partida");
        
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        
        fileChooser.setInitialFileName(fechaHora+".txt");
        
        return fileChooser.showSaveDialog(App.scene.getWindow());
    }
    
}
