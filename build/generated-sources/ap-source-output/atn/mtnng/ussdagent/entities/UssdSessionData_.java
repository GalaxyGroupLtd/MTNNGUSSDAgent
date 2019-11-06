package atn.mtnng.ussdagent.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T11:07:08")
@StaticMetamodel(UssdSessionData.class)
public class UssdSessionData_ { 

    public static volatile SingularAttribute<UssdSessionData, String> codeScheme;
    public static volatile SingularAttribute<UssdSessionData, String> serviceCode;
    public static volatile SingularAttribute<UssdSessionData, String> dest_url;
    public static volatile SingularAttribute<UssdSessionData, Integer> sessionType;
    public static volatile SingularAttribute<UssdSessionData, String> sessionId;
    public static volatile SingularAttribute<UssdSessionData, String> msisdn;
    public static volatile SingularAttribute<UssdSessionData, String> spId;
    public static volatile SingularAttribute<UssdSessionData, Date> initTime;
    public static volatile SingularAttribute<UssdSessionData, String> shortCode;

}