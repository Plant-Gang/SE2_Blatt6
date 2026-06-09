package de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.wertobjekte.Kundennummer;

public class VormerkKarteTest
{
    private VormerkKarte _karte;
    private Medium _medium;
    private Kunde _kunde1;
    private Kunde _kunde2;
    private Kunde _kunde3;

    public VormerkKarteTest()
    {
        _medium = new CD("CD Titel", "Kommentar", "Interpret", 100);

        _kunde1 = new Kunde(new Kundennummer(111111), "A", "a");
        _kunde2 = new Kunde(new Kundennummer(222222), "B", "b");
        _kunde3 = new Kunde(new Kundennummer(333333), "C", "c");

        _karte = new VormerkKarte(_medium);
    }

    @Test
    public void testNeueVormerkKarte()
    {
        assertEquals(_medium, _karte.getMedium());
        assertFalse(_karte.istVorgemerkt());
        assertTrue(_karte.hatFreienVormerkplatz());
        assertNull(_karte.getVormerker(0));
        assertNull(_karte.getVormerker(1));
        assertNull(_karte.getVormerker(2));
    }

    @Test
    public void testEinKundeWirdVorgemerkt()
    {
        _karte.merkeVor(_kunde1);

        assertTrue(_karte.istVorgemerkt());
        assertTrue(_karte.istVorgemerktFuer(_kunde1));
        assertEquals(_kunde1, _karte.getErstenVormerker());
        assertEquals(_kunde1, _karte.getVormerker(0));
        assertNull(_karte.getVormerker(1));
        assertNull(_karte.getVormerker(2));
    }

    @Test
    public void testMehrereKundenWerdenInRichtigerReihenfolgeVorgemerkt()
    {
        _karte.merkeVor(_kunde1);
        _karte.merkeVor(_kunde2);
        _karte.merkeVor(_kunde3);

        assertTrue(_karte.istVorgemerkt());
        assertEquals(_kunde1, _karte.getVormerker(0));
        assertEquals(_kunde2, _karte.getVormerker(1));
        assertEquals(_kunde3, _karte.getVormerker(2));
        assertEquals(_kunde1, _karte.getErstenVormerker());
    }

    @Test
    public void testMaximaleAnzahlVonDreiVormerkern()
    {
        assertTrue(_karte.hatFreienVormerkplatz());

        _karte.merkeVor(_kunde1);
        assertTrue(_karte.hatFreienVormerkplatz());

        _karte.merkeVor(_kunde2);
        assertTrue(_karte.hatFreienVormerkplatz());

        _karte.merkeVor(_kunde3);
        assertFalse(_karte.hatFreienVormerkplatz());
    }

    @Test
    public void testIstVorgemerktFuer()
    {
        assertFalse(_karte.istVorgemerktFuer(_kunde1));

        _karte.merkeVor(_kunde1);

        assertTrue(_karte.istVorgemerktFuer(_kunde1));
        assertFalse(_karte.istVorgemerktFuer(_kunde2));
    }

    @Test
    public void testEntferneErstenVormerkerRuecktNach()
    {
        _karte.merkeVor(_kunde1);
        _karte.merkeVor(_kunde2);
        _karte.merkeVor(_kunde3);

        _karte.entferneErstenVormerker();

        assertTrue(_karte.istVorgemerkt());
        assertEquals(_kunde2, _karte.getErstenVormerker());
        assertEquals(_kunde2, _karte.getVormerker(0));
        assertEquals(_kunde3, _karte.getVormerker(1));
        assertNull(_karte.getVormerker(2));
        assertTrue(_karte.hatFreienVormerkplatz());
    }

    @Test
    public void testEntferneAlleVormerker()
    {
        _karte.merkeVor(_kunde1);
        _karte.merkeVor(_kunde2);

        _karte.entferneErstenVormerker();
        assertTrue(_karte.istVorgemerkt());
        assertEquals(_kunde2, _karte.getErstenVormerker());

        _karte.entferneErstenVormerker();
        assertFalse(_karte.istVorgemerkt());
        assertNull(_karte.getVormerker(0));
        assertTrue(_karte.hatFreienVormerkplatz());
    }
}