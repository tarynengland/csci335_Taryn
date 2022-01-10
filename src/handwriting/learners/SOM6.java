package handwriting.learners;

import handwriting.gui.DrawingEditor;
import handwriting.gui.DrawingEditorController;
import handwriting.learners.som.SOMRecognizer;

public class SOM6 extends SOMRecognizer {

    public SOM6() {
        super(6, DrawingEditor.DRAWING_WIDTH, DrawingEditor.DRAWING_HEIGHT);
    }
}
