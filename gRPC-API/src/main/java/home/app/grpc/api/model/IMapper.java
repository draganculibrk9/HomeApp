package home.app.grpc.api.model;

public interface IMapper<TEntity, TDTO> {
    TEntity toEntity(TDTO dto);

    TDTO toDTO(TEntity entity);
}
