export interface ElectricityProduction {
    id: number;
    wind_farm_id: string;
    timestamp: string;
    electricity_produced_mw: string;
    capacity_factor: number;
}
