package Tools;

import ObjectsForTests.Posts;

import java.util.Comparator;

public class PostsComparator implements Comparator<Posts> {
    @Override
    public int compare(Posts o1, Posts o2) {
        int result = o1.getId() - o2.getId();
        if (result==0) {
            result = o1.getTitle().compareTo(o2.getTitle());
            if (result==0) {
                result = o1.getBody().compareTo(o2.getBody());
                if (result==0) {
                    result = o1.getUserId() - o2.getUserId();
                }
            }
        }
        return result;
    }
}
