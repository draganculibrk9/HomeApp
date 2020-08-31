package home.app.services.service.mappers;


public interface IMapper<TEntity, TDTO> {
    TEntity toEntity(TDTO dto);

    TDTO toDTO(TEntity entity);
}
