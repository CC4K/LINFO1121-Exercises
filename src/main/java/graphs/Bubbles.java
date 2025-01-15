package graphs;

import java.util.*;

/**
 * Sophie and Marc want to reduce the bubbles
 * of contacts in the belgian population
 * to contain an evil virus (weird idea but
 * nevertheless inspired by a true belgian
 * story in 2020, don't ask ...).
 *
 * Help them!
 *
 * The Belgian government has imposed on the
 * population to limit the number of contacts, via "bubbles".
 *
 * The principle is quite simple:
 * If you have a (close) contact with someone,
 * You are then in his bubble, and he is in yours.
 *
 * Let's say the following contacts have taken place: [
 * [Alice, Bob], [Alice, Carole], [Carole, Alice], [Eve, Alice], [Alice, Frank],
 * [Bob, Carole], [Bob, Eve], [Bob, Frank], [Bob, Carole], [Eve, Frank]
 * ].
 *
 * Note that the contacts are two-by-two and
 * can occur several times. The order within
 * of a contact does not matter.
 *
 * The resulting bubbles are :
 *
 * - Alice's bubble = [Bob, Carol, Eve, Frank]
 * - Bob's bubble = [Alice, Carol, Eve, Frank]
 * - Carole's bubble = [Alice, Bob]
 * - Eve's bubble = [Alice, Bob, Frank]
 * - Frank's Bubble = [Alice, Bob, Eve]
 *
 * Note that the relationship is symmetric
 * (if Alice is in Bob's bubble, then Bob is in Alice's bubble)
 * but not transitive (if Bob is in Alice's bubble,
 * then Bob is in Alice's bubble)
 * and Carol is in Bob's bubble, Carol is
 * not necessarily in Alice's.
 *
 * Since at most n people can be in someone's
 * bubble without him being outlaw
 * given a list of contacts, select pairs of people
 * that you will forbid to meet, so that eventually
 * each person has a bubble of NO MORE than n people
 * (not counting themselves).
 * You need to ban AS FEW AS POSSIBLE (pairs of) people to meet.
 *
 * For example, if n = 3, in the example above,
 * you could forbid Alice and Carol to see each other, but also
 * Bob and Carol. This removes 2 links
 * (even though Alice and Carol appear twice in the contacts!).
 * But there is a better solution: prevent Alice and Bob
 * from seeing each other, which only removes one link.
 * Finding an algorithm that solves this problem is complex,
 * that's why we give you a rather vague idea of an algorithm:
 *
 * - As long as there are links between two bubbles
 *   each "too big", remove one of these links;
 * - Then, as long as there are bubbles that are too big,
 *   remove any link connected to one of these bubbles
 *   (removing is equivalent to "adding the link
 *   to the list of forbidden relationships")
 *
 * Implementing this algorithm as it is a bad idea.
 * Think of an optimal way to implement it in the
 * method {@code cleanBubbles}
 *
 * You CANNOT modify the `contacts` list directly (nor the lists inside)
 * If you try, you will receive an UnsupportedOperationException.
 *
 */
public class Bubbles {

    /**
     *
     * @param contacts
     * @param n the number of persons in the population ranges from 0 to n-1 (included)
     * @return a list of people you are going to forbid to see each other.
     *         There MUST NOT be any duplicates.
     *         The order doesn't matter, both within the
     *         ForbiddenRelation and in the list.
     */
    public static List<ForbiddenRelation> cleanBubbles(List<Contact> contacts, int n) {
        // TODO
        // create bubbles from given contacts
        HashMap<String, HashSet<String>> bubbleList = new HashMap<>();
        for (Contact contact : contacts) {
            if (!bubbleList.containsKey(contact.a)) {
                bubbleList.put(contact.a, new HashSet<>());
            }
            if (!bubbleList.containsKey(contact.b)) {
                bubbleList.put(contact.b, new HashSet<>());
            }
            bubbleList.get(contact.a).add(contact.b);
            bubbleList.get(contact.b).add(contact.a);
        }
//        System.out.println(bubbleList.entrySet());
        // return value
        List<ForbiddenRelation> ret = new ArrayList<>();
        // for each connection => prune mutual connections if both have too many contacts
        for (Contact contact : contacts) {
            // skip the check if we just removed the element from bubbleList anyway
            if (!bubbleList.get(contact.a).contains(contact.b)) continue;
            if (bubbleList.get(contact.a).size() > n && bubbleList.get(contact.b).size() > n) {
                ret.add(new ForbiddenRelation(contact.a, contact.b));
                bubbleList.get(contact.a).remove(contact.b);
                bubbleList.get(contact.b).remove(contact.a);
            }
        }
        // for each connection => prune mutual connections if at least one still has too many contacts
        for (Contact contact : contacts) {
            // skip the check if we just removed the element from bubbleList anyway
            if (!bubbleList.get(contact.a).contains(contact.b)) continue;
            if (bubbleList.get(contact.a).size() > n || bubbleList.get(contact.b).size() > n) {
                ret.add(new ForbiddenRelation(contact.a, contact.b));
                bubbleList.get(contact.a).remove(contact.b);
                bubbleList.get(contact.b).remove(contact.a);
            }
        }
//        for (ForbiddenRelation f : ret) {System.out.println(f.a+"-"+f.b);}
        return ret;
    }
}



class Contact {
    public final String a, b;

    public Contact(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }
}

class ForbiddenRelation implements Comparable<ForbiddenRelation> {
    public final String a, b;

    public ForbiddenRelation(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ForbiddenRelation)
            return a.equals(((ForbiddenRelation) obj).a) && b.equals(((ForbiddenRelation) obj).b);
        return false;
    }

    @Override
    public int compareTo(ForbiddenRelation o) {
        if(a.equals(o.a))
            return b.compareTo(o.b);
        return a.compareTo(o.a);
    }
}
