package de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten;

import java.util.ArrayList;
import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;

/**
 * Mit Hilfe von VormerkKarten werden die Vormerkungen eines Mediums
 * verwaltet.
 *
 * Sie beantwortet die folgenden Fragen: Für welches Medium besteht die
 * Vormerkung? Welche Kunden haben das Medium vorgemerkt? Wer ist der erste
 * Vormerker und darf das Medium als Nächstes ausleihen?
 *
 * Eine VormerkKarte speichert bis zu drei Vormerker in der Reihenfolge
 * ihrer Vormerkung. Wird ein vorgemerktes Medium ausgeliehen oder eine
 * Vormerkung entfernt, rücken die nachfolgenden Vormerker automatisch nach.
 * Die Verwaltung der VormerkKarten übernimmt der VerleihService.
 *
 * @author 
 * @version SoSe 2026
 */
public class VormerkKarte
{
    private static final int MAX_VORMERKER = 3;

    private final Medium _medium;
    private final List<Kunde> _vormerker;

    /**
     * Initialisiert eine neue VormerkKarte für ein Medium.
     *
     * @param medium Das Medium.
     *
     * @require medium != null
     * @ensure getMedium() == medium
     */
    public VormerkKarte(Medium medium)
    {
        assert medium != null : "Vorbedingung verletzt: medium != null";

        _medium = medium;
        _vormerker = new ArrayList<Kunde>();
    }

    /**
     * Merkt einen Kunden für das Medium vor.
     *
     * @param kunde Der Kunde.
     *
     * @require kunde != null
     * @require hatFreienVormerkplatz()
     * @require !istVorgemerktFuer(kunde)
     * @ensure istVorgemerktFuer(kunde)
     */
    public void merkeVor(Kunde kunde)
    {
        assert kunde != null : "Vorbedingung verletzt: kunde != null";
        assert hatFreienVormerkplatz() : "Vorbedingung verletzt: hatFreienVormerkplatz()";
        assert !istVorgemerktFuer(
                kunde) : "Vorbedingung verletzt: !istVorgemerktFuer(kunde)";

        _vormerker.add(kunde);
    }

    /**
     * Prüft, ob das Medium vorgemerkt ist.
     *
     * @return true, wenn mindestens ein Vormerker existiert.
     */
    public boolean istVorgemerkt()
    {
        return !_vormerker.isEmpty();
    }

    /**
     * Prüft, ob ein Kunde bereits Vormerker ist.
     *
     * @param kunde Der Kunde.
     *
     * @return true, wenn der Kunde bereits vorgemerkt hat.
     *
     * @require kunde != null
     */
    public boolean istVorgemerktFuer(Kunde kunde)
    {
        assert kunde != null : "Vorbedingung verletzt: kunde != null";
        return _vormerker.contains(kunde);
    }

    /**
     * Prüft, ob noch ein Vormerkplatz frei ist.
     *
     * @return true, wenn weniger als drei Kunden vorgemerkt sind.
     */
    public boolean hatFreienVormerkplatz()
    {
        return _vormerker.size() < MAX_VORMERKER;
    }

    /**
     * Liefert den Vormerker an einer Position.
     *
     * @param position Die Position 0, 1 oder 2.
     *
     * @return Der Vormerker oder null, falls die Position nicht besetzt ist.
     *
     * @require position >= 0
     * @require position < MAX_VORMERKER
     */
    public Kunde getVormerker(int position)
    {
        assert position >= 0 : "Vorbedingung verletzt: position >= 0";
        assert position < MAX_VORMERKER : "Vorbedingung verletzt: position < MAX_VORMERKER";

        if (position < _vormerker.size())
        {
            return _vormerker.get(position);
        }
        return null;
    }

    /**
     * Liefert den ersten Vormerker.
     *
     * @return Der erste Vormerker.
     *
     * @require istVorgemerkt()
     * @ensure result != null
     */
    public Kunde getErstenVormerker()
    {
        assert istVorgemerkt() : "Vorbedingung verletzt: istVorgemerkt()";
        return _vormerker.get(0);
    }

    /**
     * Entfernt den ersten Vormerker.
     *
     * @require istVorgemerkt()
     */
    public void entferneErstenVormerker()
    {
        assert istVorgemerkt() : "Vorbedingung verletzt: istVorgemerkt()";
        _vormerker.remove(0);
    }

    /**
     * Gibt das zugehörige Medium zurück.
     *
     * @return Das Medium.
     *
     * @ensure result != null
     */
    public Medium getMedium()
    {
        return _medium;
    }
}