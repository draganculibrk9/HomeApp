package home.app.auth.service.mappers;

public interface IMapper<TEntity, TDTO> {
    TEntity toEntity(TDTO dto) throws Exception;

    TDTO toDTO(TEntity entity);
}
