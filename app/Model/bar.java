package Model;

  import play.play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by oscar on 12/08/15.
 */

    @Entity
    puclic class Bar extends Model {

            @Id
            public String Id;
            public string name;


}
