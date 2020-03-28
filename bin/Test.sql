INSERT INTO RP_MOV_EGRESODET
    (
    DESCUENTOME,
    DESCTO_UNIME,
    IVA_UNIME,
    PRECIOME,
    PRECIO_VENTAME,
    IVA_UNI,
    DESCTO_UNI,
    PRECIO,
    EXIS_ANT,
    COSTO_ANT,
    DESCUENTO,
    PORCENTAJE,
    PRECIO_LISTA,
    PRECIO_VENTA,
    COSTO_UNITARIO,
    COSTO_REPOSICION,
    CANTIDAD,
    IVA_SRV,
    EXIST_CALC,
    COSTO_CALC,
    ID_REPUESTOO,
    ID_REPUESTO,
    CORRELATIVO,
    ID_EGRESO,
    FLG_VENTA_ADICIONAL,
    FLG_DEVOLUCION
    )
VALUES
    (
        :DESCUENTOME,
        :DESCTO_UNIME,
        :IVA_UNIME,
        :PRECIOME,
        :PRECIO_VENTAME,
        :IVA_UNI,
        :DESCTO_UNI,
        :PRECIO,
        0,
        0,
        :DESCUENTO,
        :PORCENTAJE,
        :PRECIO_LISTA,
        :PRECIO_VENTA,
        :COSTO_UNITARIO,
        :COSTO_REPOSICION,
        :CANTIDAD,
        0,
        0,
        0,
        :OID_REPUESTO,
        :ID_REPUESTO,
        :CORRELATIVO,
        :ID_EGRESO,
        'N',
        'N'
)