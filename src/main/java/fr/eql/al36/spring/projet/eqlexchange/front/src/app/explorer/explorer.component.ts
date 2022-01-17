import {Component, Directive, OnInit} from '@angular/core';


const ELEMENT_DATA: {}[] = [
  {id: 1, name: 'Bitcoin', price: 1.0079, supply: '10000'},
  {id: 1, name: 'Ethereum', price: 30000, supply: '900000'},

];

@Component({
  selector: 'app-explorer',
  templateUrl: './explorer.component.html',
  styleUrls: ['./explorer.component.scss']
})


export class ExplorerComponent implements OnInit {

  displayedColumns: string[] = ['#', 'Crypto', 'Price', 'Supply', 'Actions'];
  dataSource = ELEMENT_DATA;

  constructor() { }

  ngOnInit(): void {
  }

}
