package hibernate;

import org.hibernate.type.EnumType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Types;
import java.util.Properties;

import static org.hibernate.type.EnumType.TYPE;


public class HibernateVarcharEnum extends EnumType {

    private static final Logger logger = LoggerFactory.getLogger(HibernateVarcharEnum.class);

    public void setParameterValues(Properties parameters) {
        parameters.setProperty(TYPE, "" + Types.VARCHAR);
        super.setParameterValues(parameters);
    }
}
