import { inject, Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class SnackBarService {
  constructor() {}

  private _snackBar = inject(MatSnackBar);

  openSnackBar(message: string) {
    this._snackBar.open(message, 'Close')._dismissAfter(5000);
  }
}
