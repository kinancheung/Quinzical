echo "Launching application..."
java --module-path ~/usr/share/java/lib --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml -jar QuinzicalGroup12.jar &

disown

exit
