package com.quadient.wfdxml.internal.module

import com.quadient.wfdxml.api.Node
import com.quadient.wfdxml.api.layoutnodes.FillStyle
import com.quadient.wfdxml.api.layoutnodes.TextStyle
import com.quadient.wfdxml.api.layoutnodes.tables.Cell
import com.quadient.wfdxml.api.layoutnodes.tables.RowSet
import com.quadient.wfdxml.api.layoutnodes.tables.Table
import com.quadient.wfdxml.api.module.Layout
import com.quadient.wfdxml.internal.Group
import com.quadient.wfdxml.internal.layoutnodes.TextStyleImpl
import com.quadient.wfdxml.internal.module.layout.LayoutImpl
import com.quadient.wfdxml.internal.xml.export.XmlExporter
import spock.lang.Specification

import static com.quadient.wfdxml.utils.AssertXml.assertXmlFileEquals

class LayoutImplTest extends Specification {
    private XmlExporter exporter = new XmlExporter()

    def "empty layout serialization"() {
        given:
        def emptyLayout = new LayoutImpl()

        when:
        emptyLayout.export(exporter)

        then:
        assertXmlFileEquals('com/quadient/wfdxml/workflow/EmptyLayout.xml', exporter.buildString())
    }

    def "addTable"() {
        given:
        def layout = new LayoutImpl()

        when:
        Table table = layout.addTable()

        then:
        assert table instanceof Table
        assertAdd(layout, table, "Tables")
    }

    def "addRowSet"() {
        given:
        def layout = new LayoutImpl()

        when:
        RowSet rowSet = layout.addRowSet()

        then:
        assert rowSet instanceof RowSet
        assertAdd(layout, rowSet, "RowSets")
    }

    def "addCell"() {
        given:
        def layout = new LayoutImpl()

        when:
        Cell cell = layout.addCell()

        then:
        assert cell instanceof Cell
        assertAdd(layout, cell, "Cells")
    }

    void assertAdd(LayoutImpl layout, Node newNode, String expectedGroupName) {
        Group tables = layout.children.find { it.name == expectedGroupName } as Group
        assert tables.children.size() == 1
        assert tables.children[0].is(newNode)
    }


    def "export addBulletFlow"() {
        given:
        TextStyle textStyle = new TextStyleImpl()
        Layout layout = new LayoutImpl()
        layout.addBulletParagraph(textStyle, "l\t").setName("BullettingFlow")

        when:
        layout.export(exporter)

        then:
        String result = exporter.buildString()

        assert result.contains("BullettingFlow")
        result.contains("""l<Tab></Tab>""")

    }

    def "export addBulletParagraph"() {
        given:
        TextStyle textStyle = new TextStyleImpl()
        Layout layout = new LayoutImpl()
        layout.addBulletParagraph(textStyle, "m\t").setFirstLineLeftIndent(0.005d)

        when:
        layout.export(exporter)

        then:
        String result = exporter.buildString()

        assert result.contains("""m<Tab></Tab>""")
        result.contains("<FirstLineLeftIndent>0.005</FirstLineLeftIndent>")
    }

    def "addFillStyle without color set def.Color Black"() {
        when:
        Layout layout = new LayoutImpl()
        FillStyle fillStyle = layout.addFillStyle()

        then:
        fillStyle.getColor().getName() == "Black"
    }
}