import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Button} from "primeng/button";
import {TableModule} from "primeng/table";
import {ElectricityProductionService} from "../../core/services/electricity-production.service";
import {WindFarm} from "../../core/models/wind-farm.model";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Tag} from "primeng/tag";
import {DialogModule} from "primeng/dialog";
import {DecimalPipe, NgIf} from "@angular/common";
import {Page} from "../../core/models/page.model";
import {ElectricityProduction} from "../../core/models/electricity-production.model";
import {Paginator} from "primeng/paginator";
import {DatePicker} from "primeng/datepicker";
import {Divider} from "primeng/divider";
import {InputText} from "primeng/inputtext";
import {FloatLabel} from "primeng/floatlabel";
import {WindFarmService} from "../../core/services/wind-farm.service";
import {TimezoneService} from "../../core/services/timezone.service";
import {Select, SelectChangeEvent} from "primeng/select";
import {map} from "rxjs";

interface Options {
    name: string;
    code: string;
}


@Component({
    selector: 'app-wind-farm-tab-table',
    imports: [
        TableModule,
        FormsModule,
        Tag,
        Button,
        DialogModule,
        DecimalPipe,
        Paginator,
        DatePicker,
        Divider,
        ReactiveFormsModule,
        InputText,
        NgIf,
        FloatLabel,
        Select,

    ],
    standalone: true,
    templateUrl: './wind-farm-tab-table.component.html',
    styleUrl: './wind-farm-tab-table.component.css'
})
export class WindFarmTabTableComponent {

    @Input()
    windFarms: WindFarm[] = [];

    @Output()
    windFarmChanged: EventEmitter<void> = new EventEmitter<void>();

    electricityProductionPage: Page<ElectricityProduction> = {
        content: [],
        totalPages: 0,
        totalElements: 0,
        size: 5,
        number: 0,
        numberOfElements: 0,
        first: false,
        last: false
    };

    isWindFarmCreateDialogOpen: boolean = false;
    isDetailsDialogOpen: boolean = false;

    first = 0;
    rows = 10;
    pageNumber = 0;

    private id: number = 0;
    openWindFarm: WindFarm | undefined;
    electricityProductionData: ElectricityProduction[] = [];
    loading: boolean = true;
    averageCapacityFactor: number = 0;

    windFarmForm!: FormGroup;
    timezonesOptions: Options[] = [];
    selectedTimezone: string = '';
    dateTimeFromFilter: Date | null = null;
    dateTimeToFilter: Date | null = null;

    constructor(private windFarmService: WindFarmService,
                private electricityProductionService: ElectricityProductionService,
                private timezoneService: TimezoneService,
                private fb: FormBuilder) {
    }

    openWindFarmCreateDialog() {
        this.windFarmForm = this.fb.group({
            capacityMw: [null, [Validators.required, Validators.min(0)]],
            description: [null, [Validators.required, Validators.maxLength(255)]],
            timezone: [null, [Validators.required]],
            location: [null, [Validators.required]],
        });

        this.timezoneService.getTimezones()
            .pipe(
                map(data =>
                    data.map((timezone: string) => ({
                        name: timezone,
                        code: timezone
                    }))
                )
            )
            .subscribe(mappedData => {
                this.timezonesOptions = mappedData;
            });
        this.isWindFarmCreateDialogOpen = true;
    }

    onDetailsDialogVisibleChange($event: boolean) {
        this.isDetailsDialogOpen = $event;
    }

    openWindFarmTabTableDetailsDialog(id: number) {
        this.id = id;
        this.openWindFarm = this.windFarms.find(wf => wf.id === id);
        this.isDetailsDialogOpen = true;
        this.dateTimeFromFilter = null;
        this.dateTimeToFilter = null;
        this.loadData(this.id, this.rows, this.pageNumber);
    }

    onWindFarmCreationDialogVisibleChange($event: boolean) {
        this.isWindFarmCreateDialogOpen = $event;
    }


    next() {
        console.log('first' + this.first);
        console.log('rows' + this.rows);
        this.first = this.first + this.rows;
        this.pageNumber = this.pageNumber + 1;
        this.loadData(this.id, this.rows, this.pageNumber);
        console.log(this.first);
        console.log(this.rows);
    }

    prev() {
        console.log(this.first);
        console.log(this.rows);
        this.first = this.first - this.rows;
        this.pageNumber = this.pageNumber - 1;
        this.loadData(this.id, this.rows, this.pageNumber);
        console.log(this.first);
        console.log(this.rows);
    }

    reset() {
        this.first = 0;
        this.pageNumber = 0;
        this.loadData(this.id, this.rows, this.pageNumber);
    }

    pageChange(event: any) {
        this.first = event.first;
        this.rows = event.rows;
        this.pageNumber = event.page;

        this.loadData(this.id, this.rows, this.pageNumber);
    }

    isLastPage(): boolean {
        return this.electricityProductionPage.content.length > 0 ? this.first + this.rows >= this.electricityProductionPage.totalElements : true;
    }

    isFirstPage(): boolean {
        return this.electricityProductionPage.content.length > 0 ? this.first === 0 : true;
    }

    loadData(id: number, pageSize: number, pageNumber: number) {
        const timezone = this.openWindFarm?.timezone ?? 'Europe/Warsaw';
        const dateFrom: Date | null = this.dateTimeFromFilter;
        const dateTo: Date | null = this.dateTimeToFilter;

        this.electricityProductionService.getElectricityProduction(id, timezone, dateFrom, dateTo, pageSize, pageNumber).subscribe((data: any) => {
            this.electricityProductionPage = data;
            this.electricityProductionData = data.content;
            this.loading = false;
        })

        this.electricityProductionService.getAverageCapacityFactor(id, dateFrom, dateTo).subscribe((data: number) => {
            this.averageCapacityFactor = data ? Number(data.toFixed(2)) : 0;
        })
    }

    onDateChange() {
        this.loadData(this.id, this.rows, this.pageNumber);
    }

    submitWindFarmForm() {
        this.windFarmService.createWindFarm(this.windFarmForm.value)
            .subscribe({
                next: () => {
                    this.isWindFarmCreateDialogOpen = false;
                    this.windFarmChanged.emit();
                },
                error: () => {
                }
            });
    }

    onTimezoneChange($event: SelectChangeEvent) {
        this.selectedTimezone = this.windFarmForm.controls['timezone'].value;
    }
}
