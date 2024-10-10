package com.quadient.wfdxml.api.layoutnodes.flow;

import com.quadient.wfdxml.api.layoutnodes.Flow;
import com.quadient.wfdxml.api.layoutnodes.ParagraphStyle;

public interface Paragraph {
    Paragraph setParagraphStyle(ParagraphStyle paragraphStyle);

    Text addText();

    Flow back();
}