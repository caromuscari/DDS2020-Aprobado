package ar.edu.utn.frba.dds.Entidad;

public enum TipoActividad {
    CONSTRUCCION{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(int ingresosPromedio) {
            if(ingresosPromedio < 15230000){
                return new MicroEmpresa();
            }else if(ingresosPromedio < 90310000){
                return new PequenaEmpresa();
            }else if(ingresosPromedio < 503880000){
                return new MedianaT1Empresa();
            }else{
                return new MedianaT2Empresa();
            }
        }
    },
    SERVICIOS{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(int ingresosPromedio) {
            if(ingresosPromedio < 8500000){
                return new MicroEmpresa();
            }else if(ingresosPromedio < 50950000){
                return new PequenaEmpresa();
            }else if(ingresosPromedio < 425170000){
                return new MedianaT1Empresa();
            }else{
                return new MedianaT2Empresa();
            }
        }
    },
    COMERCIO{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(int ingresosPromedio) {
            if(ingresosPromedio < 29740000){
                return new MicroEmpresa();
            }else if(ingresosPromedio < 178860000){
                return new PequenaEmpresa();
            }else if(ingresosPromedio < 1502750000){
                return new MedianaT1Empresa();
            }else{
                return new MedianaT2Empresa();
            }
        }
    },
    INDUSTRIAYMINERIA{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(int ingresosPromedio) {
            if(ingresosPromedio < 26540000){
                return new MicroEmpresa();
            }else if(ingresosPromedio < 190410000){
                return new PequenaEmpresa();
            }else if(ingresosPromedio < 1190330000){
                return new MedianaT1Empresa();
            }else{
                return new MedianaT2Empresa();
            }
        }
    },
    AGROPECUARIO{
        @Override
        public TipoEmpresa obtenerTipoEmpresa(int ingresosPromedio) {
            if(ingresosPromedio < 12890000){
                return new MicroEmpresa();
            }else if(ingresosPromedio < 48480000){
                return new PequenaEmpresa();
            }else if(ingresosPromedio < 345430000){
                return new MedianaT1Empresa();
            }else{
                return new MedianaT2Empresa();
            }
        }
    };

    public abstract TipoEmpresa obtenerTipoEmpresa(int ingresosPromedio);
}
