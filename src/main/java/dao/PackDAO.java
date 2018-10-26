package dao;

import entity.Pack;

public interface PackDAO {

    int save(Pack pack);

    int update(Pack pack);

    Pack findByPid(long pid);
}
