import com.search.pkgs.datacanal.listener.MysqlListener;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-19 22:26
 */
public class CanalApp {

    public static void main(String[] args) {
        new MysqlListener().exec();
    }

}
