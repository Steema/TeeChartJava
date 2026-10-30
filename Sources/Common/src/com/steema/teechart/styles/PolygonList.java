package com.steema.teechart.styles;


import java.util.ArrayList;

    /**
     * ValueLists is a collection of <see cref="ValueList"/> objects.
     *
     * <p>Title: TeeChart for Java</p>
     *
     * <p>Description: A collection of {@link ValueList} objects.</p>
     *
     * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
     * Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    final public class PolygonList extends ArrayList {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Map owner;

        public int add(Polygon value) {
            super.add(value);
            int index = super.size() - 1;
            value.setIndex(index);
            return index;
        }

        public int indexOf(Series s) {
            return super.indexOf(s);
        }

        /*
                     public void addRange(ICollection c) {
            Object[] array = new Object[c.Count - 1];
            c.CopyTo(array, 0);

            for (int t = 0; t < array.length; t++) {
                add((Polygon) array[t]);
            }
                     }
         */

        public void removeRange(int index, int count) {
            for (int i = index; i <= count; ++i) {
                remove(i);
            }
        }

        public void assign(PolygonList value) {
            clear();
            add(value);
        }

        public Polygon getPolygon(int index) {
            return (Polygon) get(index);
        }

        public Polygon getPolygon(String name) {
            return getByName(name);
        }

        public void setPolygon(int index, Polygon value) {
            set(index, value);
        }

        private Polygon getByName(String name) {
            Polygon result = null;
            String tmp = name.toUpperCase();

            for (int i = 0; i < this.size(); ++i) {
                if (get(i).toString().toUpperCase().equals(tmp)) {
                    result = getPolygon(i);
                    break;
                }
            }
            return result;
        }

        public PolygonList(Map map) {
            super();
            owner = map;
        }

        /**
         * The Map Series that owns the PolygonList.
         *
         * @return Map
         */
        public Map getOwner() {
            return owner;
        }

        /**
         * Sets the Map Series that owns the PolygonList.
         *
         * @param value Map
         */
        public void setOwner(Map value) {
            owner = value;
        }
    }