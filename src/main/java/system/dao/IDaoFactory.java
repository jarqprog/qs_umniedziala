package system.dao;

public interface IDaoFactory {

    <T extends Dao> T create(Class<T> daoType);

}
