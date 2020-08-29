import { Component, OnInit } from '@angular/core';
import {TokenService} from '../../services/token.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  constructor(public tokenService: TokenService) { }

  ngOnInit(): void {
  }

}
