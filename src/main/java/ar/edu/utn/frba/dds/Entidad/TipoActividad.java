package ar.edu.utn.frba.dds.Entidad;

public enum TipoActividad {
    CONSTRUCCION{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(Double ingresosPromedio) {
            if(ingresosPromedio < 15230000){
                return TipoEmpresa.Micro;
            }else if(ingresosPromedio < 90310000){
                return TipoEmpresa.Pequeña;
            }else if(ingresosPromedio < 503880000){
                return TipoEmpresa.MedianaT1;
            }else{
                return TipoEmpresa.MedianaT2;
            }
        }
    },
    SERVICIOS{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(Double ingresosPromedio) {
            if(ingresosPromedio < 8500000){
                return TipoEmpresa.Micro;
            }else if(ingresosPromedio < 50950000){
                return TipoEmpresa.Pequeña;
            }else if(ingresosPromedio < 425170000){
                return TipoEmpresa.MedianaT1;
            }else{
                return TipoEmpresa.MedianaT2;
            }
        }
    },
    COMERCIO{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(Double ingresosPromedio) {
            if(ingresosPromedio < 29740000){
                return TipoEmpresa.Micro;
            }else if(ingresosPromedio < 178860000){
                return TipoEmpresa.Pequeña;
            }else if(ingresosPromedio < 1502750000){
                return TipoEmpresa.MedianaT1;
            }else{
                return TipoEmpresa.MedianaT2;
            }
        }
    },
    INDUSTRIAYMINERIA{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(Double ingresosPromedio) {
            if(ingresosPromedio < 26540000){
                return TipoEmpresa.Micro;
            }else if(ingresosPromedio < 190410000){
                return TipoEmpresa.Pequeña;
            }else if(ingresosPromedio < 1190330000){
                return TipoEmpresa.MedianaT1;
            }else{
                return TipoEmpresa.MedianaT2;
            }
        }
    },
    AGROPECUARIO{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(Double ingresosPromedio) {
            if(ingresosPromedio < 12890000){
                return TipoEmpresa.Micro;
            }else if(ingresosPromedio < 48480000){
                return TipoEmpresa.Pequeña;
            }else if(ingresosPromedio < 345430000){
                return TipoEmpresa.MedianaT1;
            }else{
                return TipoEmpresa.MedianaT2;
            }
        }
    };

    public abstract TipoEmpresa obtenerTipoEmpresa(Double ingresosPromedio);
}
