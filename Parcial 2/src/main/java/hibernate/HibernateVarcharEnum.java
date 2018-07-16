package hibernate;

import org.hibernate.type.EnumType;

import java.sql.Types;
import java.util.Properties;

import static org.hibernate.type.EnumType.TYPE;


public class HibernateVarcharEnum extends EnumType {


    public void setParameterValues(Properties parameters) {
        parameters.setProperty(TYPE, "" + Types.VARCHAR);
        super.setParameterValues(parameters);
    }
}
