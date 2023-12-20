module com.example.island_algorithm_visualiser {
    requires javafx.controls;
    requires javafx.fxml;
            
            requires com.dlsc.formsfx;
                        
    opens com.example.island_algorithm_visualiser to javafx.fxml;
    exports com.example.island_algorithm_visualiser;
}