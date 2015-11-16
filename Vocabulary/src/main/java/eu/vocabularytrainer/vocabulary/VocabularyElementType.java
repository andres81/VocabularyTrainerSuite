/**
 * VocabularyTrainer Copyright (C) 2015 André Schepers andreschepers81@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.vocabularytrainer.vocabulary;

/**
 *
 * @author Andre Schepers <andre@team51.nl>
 */
public enum VocabularyElementType {

    TEXT("text"),
    IMAGE("image"),
    AUDIO("audio"),
    VIDEO("video");
    
    private final String value;

    VocabularyElementType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VocabularyElementType fromValue(String v) {
        for (VocabularyElementType c: VocabularyElementType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
