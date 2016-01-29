package com.itextpdf.core.pdf;

import com.itextpdf.core.PdfException;
import com.itextpdf.core.geom.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A representation of an array as described in the PDF specification. A PdfArray can contain any
 * subclass of {@link com.itextpdf.core.pdf.PdfObject}.
 */
public class PdfArray extends PdfObject implements Collection<PdfObject> {

    private List<PdfObject> list;

    /**
     * Create a new, empty PdfArray.
     */
    public PdfArray() {
        super();
        list = new ArrayList<>();
    }

    /**
     * Create a new PdfArray with the provided PdfObject as the first item in the
     * array.
     *
     * @param obj first item in the array
     */
    public PdfArray(PdfObject obj) {
        this();
        list.add(obj);
    }

    /**
     * Create a new PdfArray. The array is filled with the items of the provided PdfArray.
     *
     * @param arr PdfArray containing items that will added to this PdfArray
     */
    public PdfArray(PdfArray arr) {
        this();
        list.addAll(arr.list);
    }

    /**
     * Create a new PdfArray. The array is filled with the four values of the Rectangle in the
     * follozing order: left, bottom, right, top.
     *
     * @param rectangle Rectangle whose 4 values will be added to the PdfArray
     */
    public PdfArray(Rectangle rectangle) {
        list = new ArrayList<>(4);
        add(new PdfNumber(rectangle.getLeft()));
        add(new PdfNumber(rectangle.getBottom()));
        add(new PdfNumber(rectangle.getRight()));
        add(new PdfNumber(rectangle.getTop()));
    }

    /**
     * Create a new PdfArray. The PdfObjects in the list will be added to the PdfArray.
     *
     * @param objects List of PdfObjects to be added to this PdfArray
     */
    public PdfArray(List<? extends PdfObject> objects) {
        list = new ArrayList<>(objects.size());
        for (PdfObject element : objects)
            add(element);
    }

    /**
     * Create a new PdfArray filled with the values in the float[] as {@link com.itextpdf.core.pdf.PdfNumber}.
     *
     * @param numbers values to be added to this PdfArray
     */
    public PdfArray(float[] numbers) {
        list = new ArrayList<>(numbers.length);
        for (float f : numbers) {
            list.add(new PdfNumber(f));
        }
    }

    /**
     * Create a new PdfArray filled with the values in the double[] as {@link com.itextpdf.core.pdf.PdfNumber}.
     *
     * @param numbers values to be added to this PdfArray
     */
    public PdfArray(double[] numbers) {
        list = new ArrayList<>(numbers.length);
        for (double f : numbers) {
            list.add(new PdfNumber(f));
        }
    }

    /**
     * Create a new PdfArray filled with the values in the int[] as {@link com.itextpdf.core.pdf.PdfNumber}.
     *
     * @param numbers values to be added to this PdfArray
     */
    public PdfArray(int[] numbers) {
        list = new ArrayList<>(numbers.length);
        for (float i : numbers) {
            list.add(new PdfNumber(i));
        }
    }

    /**
     * Create a new PdfArray filled with the values in the boolean[] as {@link com.itextpdf.core.pdf.PdfBoolean}.
     *
     * @param values values to be added to this PdfArray
     */
    public PdfArray(boolean[] values) {
        list = new ArrayList<>(values.length);
        for (boolean b : values) {
            list.add(new PdfBoolean(b));
        }
    }

    /**
     * Create a new PdfArray filled with a list of Strings. The boolean value decides if the Strings
     * should be added as {@link com.itextpdf.core.pdf.PdfName} (true) or as {@link com.itextpdf.core.pdf.PdfString} (false).
     *
     * @param strings list of strings to be added to the list
     * @param asNames indicates whether the strings should be added as PdfName (true) or as PdfString (false)
     */
    public PdfArray(List<String> strings, boolean asNames) {
        list = new ArrayList<>(strings.size());
        for (String s : strings) {
            list.add(asNames ? new PdfName(s) : new PdfString(s));
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<PdfObject> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(PdfObject pdfObject) {
        return list.add(pdfObject);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends PdfObject> c) {
        return list.addAll(c);
    }

    /**
     * Adds the Collection of PdfObjects starting at the specified index.
     *
     * @param index position of the first PdfObject to be added
     * @param c the Collection of PdfObjects to be added
     * @return true if the list changed because of this operation
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    public boolean addAll(int index, Collection<? extends PdfObject> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Gets the (direct) PdfObject at the specified index.
     *
     * @param index index of the PdfObject in the PdfArray
     * @return the PdfObject at the position in the PdfArray
     */
    public PdfObject get(int index) {
        return get(index, true);
    }

    /**
     * Sets the PdfObject at the specified index in the PdfArray.
     *
     * @param index the position to set the PdfObject
     * @param element PdfObject to be added
     * @return true if the operation changed the PdfArray
     * @see java.util.List#set(int, Object)
     */
    public PdfObject set(int index, PdfObject element) {
        return list.set(index, element);
    }

    /**
     * Adds the specified PdfObject qt the specified index. All objects after this index will be shifted by 1.
     *
     * @param index position to insert the PdfObject
     * @param element PdfObject to be added
     * @see java.util.List#add(int, Object)
     */
    public void add(int index, PdfObject element) {
        list.add(index, element);
    }

    /**
     * Removes the PdfObject at the specified index.
     *
     * @param index position of the PdfObject to be removed
     * @return true if the operation changes the PdfArray
     * @see java.util.List#remove(int)
     */
    public PdfObject remove(int index) {
        return list.remove(index);
    }

    /**
     * Gets the first index of the specified PdfObject.
     *
     * @param o PdfObject to find the index of
     * @return index of the PdfObject
     * @see java.util.List#indexOf(Object)
     */
    public int indexOf(PdfObject o) {
        return list.indexOf(o);
    }

    /**
     * Gets the index of the last occurrence of the specified PdfObject.
     *
     * @param o PdfObject to find the index of
     * @return index of the PdfObject
     * @see java.util.List#lastIndexOf(Object)
     */
    public int lastIndexOf(PdfObject o) {
        return list.lastIndexOf(o);
    }

    /**
     * Returns a ListIterator.
     *
     * @return a list iterator
     * @see java.util.List#listIterator()
     */
    public ListIterator<PdfObject> listIterator() {
        return list.listIterator();
    }

    /**
     * Returns a ListIterator, which will start at the specified index.
     *
     * @param index position where the iterator should start.
     * @return ListIterator
     * @see java.util.List#listIterator(int)
     */
    public ListIterator<PdfObject> listIterator(int index) {
        return list.listIterator(index);
    }

    /**
     * Returns a sublist of this PdfArray, starting at fromIndex (inclusive) and ending at toIndex (exclusive).
     *
     * @param fromIndex the position of the first element in the sublist (inclusive)
     * @param toIndex the position of the last element in the sublist (exclusive)
     * @return List of PdfObjects
     * @see java.util.List#subList(int, int)
     */
    public List<PdfObject> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public int getType() {
        return Array;
    }

    /**
     * Marks object to be saved as indirect.
     *
     * @param document a document the indirect reference will belong to.
     * @return object itself.
     */
    @SuppressWarnings("unchecked")
    @Override
    public PdfArray makeIndirect(PdfDocument document) {
        return super.makeIndirect(document);
    }

    /**
     * Marks object to be saved as indirect.
     *
     * @param document a document the indirect reference will belong to.
     * @return object itself.
     */
    @SuppressWarnings("unchecked")
    @Override
    public PdfArray makeIndirect(PdfDocument document, PdfIndirectReference reference) {
        return super.makeIndirect(document, reference);
    }

    /**
     * Copies object to a specified document.
     * Works only for objects that are read from existing document, otherwise an exception is thrown.
     *
     * @param document document to copy object to.
     * @return copied object.
     */
    @SuppressWarnings("unchecked")
    @Override
    public PdfArray copyToDocument(PdfDocument document) {
        return super.copyToDocument(document, true);
    }

    /**
     * Copies object to a specified document.
     * Works only for objects that are read from existing document, otherwise an exception is thrown.
     *
     * @param document         document to copy object to.
     * @param allowDuplicating indicates if to allow copy objects which already have been copied.
     *                         If object is associated with any indirect reference and allowDuplicating is false then already existing reference will be returned instead of copying object.
     *                         If allowDuplicating is true then object will be copied and new indirect reference will be assigned.
     * @return copied object.
     */
    @SuppressWarnings("unchecked")
    @Override
    public PdfArray copyToDocument(PdfDocument document, boolean allowDuplicating) {
        return super.copyToDocument(document, allowDuplicating);
    }

    @Override
    public String toString() {
        String string = "[";
        for (PdfObject entry : this) {
            PdfIndirectReference indirectReference = entry.getIndirectReference();
            string = string + (indirectReference == null ? entry.toString() : indirectReference.toString()) + " ";
        }
        string += "]";
        return string;
    }

    /**
     * @param asDirect true is to extract direct object always.
     * @throws PdfException
     */
    public PdfObject get(int index, boolean asDirect) {
        if (!asDirect)
            return list.get(index);
        else {
            PdfObject obj = list.get(index);
            if (obj.getType() == IndirectReference)
                return ((PdfIndirectReference) obj).getRefersTo(true);
            else
                return obj;
        }
    }

    /**
     * Returns the element at the specified index as a PdfArray. If the element isn't a PdfArray, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a PdfArray
     */
    public PdfArray getAsArray(int index) {
        PdfObject direct = get(index, true);
        if (direct != null && direct.getType() == PdfObject.Array)
            return (PdfArray) direct;
        return null;
    }


    /**
     * Returns the element at the specified index as a PdfDictionary. If the element isn't a PdfDictionary, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a PdfDictionary
     */
    public PdfDictionary getAsDictionary(int index) {
        PdfObject direct = get(index, true);
        if (direct != null && direct.getType() == PdfObject.Dictionary)
            return (PdfDictionary) direct;
        return null;
    }


    /**
     * Returns the element at the specified index as a PdfStream. If the element isn't a PdfStream, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a PdfStream
     */
    public PdfStream getAsStream(int index) {
        PdfObject direct = get(index, true);
        if (direct != null && direct.getType() == PdfObject.Stream)
            return (PdfStream) direct;
        return null;
    }


    /**
     * Returns the element at the specified index as a PdfNumber. If the element isn't a PdfNumber, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a PdfNumber
     */
    public PdfNumber getAsNumber(int index) {
        PdfObject direct = get(index, true);
        if (direct != null && direct.getType() == PdfObject.Number)
            return (PdfNumber) direct;
        return null;
    }


    /**
     * Returns the element at the specified index as a PdfName. If the element isn't a PdfName, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a PdfName
     */
    public PdfName getAsName(int index) {
        PdfObject direct = get(index, true);
        if (direct != null && direct.getType() == PdfObject.Name)
            return (PdfName) direct;
        return null;
    }


    /**
     * Returns the element at the specified index as a PdfString. If the element isn't a PdfString, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a PdfString
     */
    public PdfString getAsString(int index) {
        PdfObject direct = get(index, true);
        if (direct != null && direct.getType() == PdfObject.String)
            return (PdfString) direct;
        return null;
    }

    /**
     * Returns the element at the specified index as a PdfBoolean. If the element isn't a PdfBoolean, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a PdfBoolean
     */
    public PdfBoolean getAsBoolean(int index) {
        PdfObject direct = get(index, true);
        if (direct != null && direct.getType() == PdfObject.Boolean)
            return (PdfBoolean) direct;
        return null;
    }

    /**
     * Returns the element at the specified index as a Rectangle. The element at the index should be a PdfArray,
     * if it isn't, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a Rectangle
     */
    public Rectangle getAsRectangle(int index) {
        PdfArray a = getAsArray(index);
        return a == null ? null : a.toRectangle();
    }

    /**
     * Returns the element at the specified index as a Float. The element at the index should be a PdfNumber,
     * if it isn't, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a Float
     */
    public Float getAsFloat(int index) {
        PdfNumber number = getAsNumber(index);
        return number == null ? null : number.getFloatValue();
    }

    /**
     * Returns the element at the specified index as an Integer. The element at the index should be a PdfNumber,
     * if it isn't, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as an Integer
     */
    public Integer getAsInt(int index) {
        PdfNumber number = getAsNumber(index);
        return number == null ? null : number.getIntValue();
    }

    /**
     * Returns the element at the specified index as a Boolean. The element at the index should be a PdfBoolean,
     * if it isn't, null is returned.
     *
     * @param index position of the element to be returned
     * @return the element at the index as a Boolean
     */
    public Boolean getAsBool(int index) {
        PdfBoolean b = getAsBoolean(index);
        return b == null ? null : b.getValue();
    }

    /**
     * Returns the first four elements of this array as a PdfArray. The first four values need to be
     * PdfNumbers, if not a PdfException will be thrown.
     *
     * @return Rectangle of the first four values
     * @throws com.itextpdf.core.PdfException if one of the first values isn't a PdfNumber
     */
    public Rectangle toRectangle() {
        try {
            float x1 = getAsNumber(0).getFloatValue();
            float y1 = getAsNumber(1).getFloatValue();
            float x2 = getAsNumber(2).getFloatValue();
            float y2 = getAsNumber(3).getFloatValue();
            return new Rectangle(x1, y1, x2 - x1, y2 - y1);
        } catch (Exception e) {
            throw new PdfException(PdfException.CannotConvertPdfArrayToRectanle, e, this);
        }
    }

    @Override
    protected PdfArray newInstance() {
        return new PdfArray();
    }

    @Override
    protected void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        PdfArray array = (PdfArray) from;
        for (PdfObject entry : array) {
            add(entry.processCopying(document, false));
        }
    }

    /**
     * Release content of PdfArray.
     */
    protected void releaseContent() {
        list = null;
    }
}
