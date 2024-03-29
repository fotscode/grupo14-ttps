import { Component } from '@angular/core'
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Router } from '@angular/router'
import { Categoria } from 'src/app/interfaces/Categoria'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { RedSocial } from 'src/app/interfaces/RedSocial'
import { User } from 'src/app/interfaces/User'
import { AuthService } from 'src/app/services/auth.service'
import { CategoriesService } from 'src/app/services/categories.service'

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private matSnackBar: MatSnackBar,
    private categoriesService: CategoriesService
  ) {}

  redes: RedSocial[] = [
    {
      nombre: '',
      url: '',
    },
  ]

  categorias: Categoria[] = []

  user: User = {
    fullName: '',
    email: '',
    password: '',
    emprendimiento: {
      domainUrl: '',
      nombre: '',
      descripcion: '',
      valorManguito: 100,
      planes: [],
      filterByDonations: true,
      filterByManguitos: true,
      imagen:
        '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vo8P3++9K5BCodDuD8XuPwjQeTlMD3RNqVQqkUjsziO0trbupcPTQRC0trZWV1fvs9FIp9N76ZFzudxujnM2m7XzFcQoQJdSU1Pz85///JZbbtmdBykUCul0eo/vwozFYk1NTeedd96+XC8plUrtjSuQampqHnnkkWeffXY307ylpWVfpjkgRgH2rlAotHPnzh07duzm8d9odK/8kiwWi1u3bt1noxEEQSQS2RvtGwqFUqlUW1vbbo6zI/UgRgG6mkgk0pGXVdpLmbvvhcNhS+IDu/VrxBAAACBGAQAQowAAIEYBABCjAAAgRgEAEKMAACBGAQDo9Cx6D3QyhUJh586d+Xx+b6+1Hg6H6+rq3uvOQOFwuKGh4b1+tr6+vsQthYIgSCQSxWJxnw1aVVVVdXV1EATv+mLi8XiJn32vH+zWrVsoFMpms6lUam+//mQyuQ+eBRCjAOX169fvm9/8ZrFY3Ks3kAyHw9u3b583b14ul3vnE0UikZaWll/84hfvdcP3HTt2pNPpd83lYrFYX18/bdq0urq694q8PfyLPhp94YUXnnvuuVgs9s5/jcfjL7744q233vquLyYUCj3//PPvVavZbHbs2LGTJ0/O5/N79S1ks9lx48b58IMYBdj/+vfvf/nll++DJ9q8efPDDz+cyWTeeVvR9hi99tprS+wyrKure9cYLRQKDQ0Nl112WX19/T4btDlz5jz11FPvFaNLlix5+umn3+tnY7FYTU3Nu77TbDY7fvz4f/u3f/OxBMQowB6WTCZL/Gs4HN7lmgyCIJlM7ssYzWaz77UjOQiCWCz2rp36j9u8V3Pv7X2iQJfnAiYAAMQoAABiFAAAxCgAAGIUAADEKAAAXY+lnYD/7+2bG5XYJplMHiCr+RSLxdbW1p07d75zndHdkcvlevTosW+Wu39bNpstO7O7wI2RADEK7EmDBw++9NJLS98cMpvNDhs27EAYjV69el188cWZTGbP3ne0UCj06NGjrq5uX76XCRMmlJ3ZXWvcY445psQGK1eufPTRR6PR9/xbEwRBOBw+66yzDjroIP8DBDEKHOiGDRv23e9+1zi069Onz6WXXto13svEiRMnTpy475936dKlM2fOLFHehUIhFotNnDhRjIIYBYA9LBaLde/evba29r02KBaL0Wh0z54IAXQuLmACAECMAgAgRgEAQIwCACBGAQBAjAIAIEYBAECMAgAgRgEAQIwCACBGAQBAjMIBKBQKdfwXmc/nzdSBw3QDYhQOIJFIpOPHQSUvIBQKdYqwZo9MdzgcDof9MQIxCnR+0Wg0CILSkZfL5fbvi0yn06U3CIIgFouJ0a4hkUiUne5oNBqNRo0ViFGg06uvry/zP/hwOJlMls3BvadQKCSTydK7wYIgqKqqMptdw44dO8rGaFVVlRkHMQp0BT179iwbozt37mxpadlfr3Dnzp07d+4se0y27Buhs9i8eXPZGK2tra2trTVWIEaBTq9fv36lN4hEIk1NTdu3b99fr7C5ubmpqansua1l3widxcaNG0ufcVEoFBoaGrp3726sQIwCnd7gwYNLnzMaDofb2trWrVu3v17hhg0bmpuby8bokCFDzGYX0NzcvHnz5tLTXSwWe/XqFYvFDBeIUaDTGzZsWCUXgrz44ov76xW+/PLLxWKx7GbDhw83m13A+vXrN27cWDY0hw0bZqxAjAJdQf/+/fv06VMoFEpsEwqFXnjhhdI7UPeexYsXlz1o27Nnz8GDB5vNLmDp0qXJZLL0jAdBMGrUKGMFYhToCgYOHDhw4MDSizfF4/EVK1a89dZb+/7lbdq06ZVXXonH4yW2yefzgwYNGjhwoNnsAp566qmyS3TFYrExY8YYKxCjQFcQj8dHjRpV+jh4JBLZtm3bk08+ue9f3tNPP71p06bSJxIUCoWRI0fW1dWZzc5u/fr1zz//fOnvHrlcbuDAgc7KADEKdB0nnnhi2UPwkUjkvvvu28er3xeLxblz55Zd1KlYLJ5wwgnmsQuYP3/+li1bSn/3yOVyo0aNsngCiFGg6zj66KN79uxZeudoVVXVCy+88PTTT+/LF7Zo0aKFCxeWXtu8WCw2NDQcd9xx5rGzSyQSd999d+ndou0zfuKJJxouEKNA13HwwQePGzcuk8mU2CYUChUKhVtvvbX0pU571i233JLJZErvGc1kMmPHjh05cqR57OzuvvvuFStWlI7R9u8ekyZNMlwgRoGuIxKJfOQjHylbmdXV1U8//fTcuXP3zauaP3/+448/XlNTU3qzQqHwkY98xJKTnV1jY+Ovf/3rsvOYyWQOP/xwl9IDYhS6mlNPPfWggw4qu8BTJBK59tpr169fv7dfz9atW3/yk5+EQqGyizr17t374x//uBns7K6++up169aVPUZfKBSmTJlSycq4gBgFOpNhw4adcsop6XS69GbxeHzDhg0zZ84su+XuyOVy3/nOd958882yaZJOpydNmjRixAgz2KnNmTPn3nvvLbseQi6XGzx48GmnnWbEADEKXdB5551XXV1d9l5HdXV1f/nLX37wgx/svTXwr7rqqvnz55dNkyAI4vH4+eefb+46tQcffPCqq66Kx+NllxdNp9NTpkwZMGCAQQPEKHRBEyZMmDx5ciqVKrtlXV3db3/72+9///v5fH7PvoYgCH784x/fdttttbW1ZTdOpVIf+tCHLOrUqf33f//3ZZddlsvlyh55z+fzffv2veCCCwwaIEahawqFQv/8z/9cU1NTdudoKBSqq6ubPXv2jBkztm7duqdeQHNz86WXXvqrX/2qtra2krVFq6qqLr744rJb0mHNmjXrkksuSSaTZc/HaP/uce655x5yyCHGDRCj0GVNmDDh7LPPTiQSlZRrXV3dvHnzzj///CeeeGL3n/qZZ5654IIL7rnnntra2rKHa7t165ZIJM4888zjjz/erHVGb7311re+9a0f/vCHxWKxkhLNZrOHHHLIRRddZOiA//dnaO+dKwbsX42NjWefffamTZtKLzX/tnQ6HYvFPvWpT335y18eN27cLjzja6+9Nnv27Llz56bT6bILObXLZDIDBgy455573I++02lubr7rrrtmz569YcOGCr94BEGQSqVuuOGGs88+2wACYhS6vvvvv3/GjBnxeLzCI+DFYjGVSvXs2fOUU0751Kc+NXHixPr6+rI/lUwmFyxY8MADDzz66KPbt2+vqamp8OmCIMjn8z//+c9PP/10k9VZ5HK511577aGHHpo3b97q1avj8XjlS8O2tbWdddZZN954YyXlCohRoCv43ve+N2vWrIaGhsp/pFAopNPpeDw+dOjQCRMmHH744QcffPCgQYN69+7dvpM1k8k0Nzdv2rRpzZo1r7zyyuLFi9966632vaGRSKTyJ0qn0+ecc077EV4z1TEFQZDL5VpbWxsbG9euXbty5crFixe/9tprO3fujMfjlRyXf1sqlRoxYsSdd97Zt29fAwuIUThQJBKJr371q3/9618r2cf5vyokn8+331m0tra2uro6Go227wPL5/O5XC6dTqdSqSAIqqqqotHoLuzrCoKgT58+4XDYL6IOq1Ao5HK5QqGQyWRSqVQul2vfFfp+rzbL5XI1NTWzZ88+9thjjSogRuHAsmnTpi984QsrVqwou97neykWi+2/K9r/s707Q3+3m61TLBYdtO2YgiD4X3O9azPVvnDYtddee8YZZxhVQIzCgWj16tVf+cpX3nzzzUpW/YQ9KJ/PF4vF73//+9OnTzcawDtZ2gkOCCNHjrzttttGjRpVyWJPsKdks9lQKPSDH/xAiQJiFA50H/jAB2bPnj1x4sTW1laHRNgHkslkQ0PD9ddf72ZLQAkO08OBZefOnVdeeeWdd94ZjUbf16XQULlisZhMJseNG3f11VePHz/egABiFPgf/vCHP1x33XWNjY2V3K4T3pd0Oh0Khc4555xvf/vbffr0MSCAGAXexeuvv/6zn/3swQcfzOfzNTU1rmdn97Uv+HX44Yf/67/+68c//nEDAohRoIzHHnvslltuWbhwYbFYrK6utpeUXRAEQTabzeVyw4YNu+CCC84///zu3bsbFkCMAhUpFouPPPLI73//+0WLFrXfVmcXljTnwGzQ9pt1RaPR0aNHn3XWWWeffXa/fv2MDCBGgV2xdOnShx566IknnlizZk1bW1soFGq/qf0eWdyeLpCe7X8visViLpfL5/NVVVX9+vU74YQTTj311MmTJ1vCFhCjwB6QyWSWLVu2ePHil156acWKFdu2bctkMplMJpfLuVXSAZuhoVCoffmFqqqqhoaGkSNHHn744ePHjz/uuONcogSIUWBvSaVSjY2N69at27JlS3NzcyKRaN8lJkkPnAwNh8OxWKympqZ79+59+/YdNGjQkCFDunfv7jMAiFEAADo91ygAACBGAQAQowAAIEYBABCjAAAgRgEAEKMAACBGAQAQowAAIEYBABCjAAAgRgEAEKMAACBGAQAQowAAIEYBABCjAACIUQAAEKMAAIhRAAAQowAAiFEAABCjAACIUQAAEKMAAIhRAAAQowAAiFEAABCjAACIUQAAEKMAAIhRAAAQowAAiFEAAMQoAACIUQAAxCgAAIhRAADEKAAAiFEAAMQoAACIUQAAxCgAAIhRAADEKAAAiFEAAMQoAACIUQAAxCgAAGIUAADEKAAAYhQAAMQoAABiFAAAxCgAAGIUAADEKAAAYhQAAMQoAABiFAAAxCgAAGIUAADEKAAAYhQAAMQoAABiFAAAMQoAAGIUAAAxCgAAYhQAADEKAABiFAAAMQoAAGIUAAAxCgAAYhQAADEKAABiFAAAMQoAAGIUAAAxCgCAGAUAADEKAIAYBQAAMQoAgBgFAAAxCgCAGAUAADEKAIAYBQAAMQoAgBgFAAAxCgCAGAUAADEKAIAYBQAAMQoAgBgFAECMAgCAGAUAQIwCAIAYBQBAjAIAgBgFAECMAgCAGAUAQIwCAIAYBQBAjAIAgBgFAECMAgCAGAUAQIwCACBGDQEAAGIUAAAxCgAAYhQAADEKAABiFAAAMQoAAGIUAAAxCgAAYhQAADEKAABiFAAAMQoAAGIUAAAxCgAAYhQAADEKAIAYBQAAMQoAgBgFAAAxCgCAGAUAADEKAIAYBQAAMQoAgBgFAAAxCgCAGAUAADEKAIAYBQAAMQoAgBgFAAAxCgCAGAUAQIwCAIAYBQBAjAIAgBgFAECMAgCAGAUAQIwCAIAYBQBAjAIAgBgFAECMAgCAGAUAQIwCAIAYBQBAjAIAIEYBAECMAgAgRgEAQIwCACBGAQBAjAIAIEYBAECMAgAgRgEAQIwCACBGAQBAjAIAIEYBAECMAgAgRgEAQIwCACBGAQAQowAAIEYBABCjAAAgRgEAEKMAACBGAQAQowAAIEYBABCjAAAgRgEAEKMAACBGAQAQowAAIEYBABCjAACIUQAAEKMAAIhRAADYJ/7vANWb/93B2SHjAAAAAElFTkSuQmCC',
      categorias: [] as Categoria[],
      posts: [],
      redesSociales: [] as RedSocial[],
      manguitos: [],
    },
  }

  repeatPassword = ''
  signUp() {
    if (this.user.password != this.repeatPassword) {
      this.matSnackBar.open('Las contraseñas no coinciden', void 0, {
        duration: 3000,
      })
      return
    }
    this.authService.signUp(this.user).subscribe(
      (res) => {
        this.matSnackBar.open('Se ha registrado correctamente', void 0, {
          duration: 3000,
        })
        this.router.navigate(['/login'])
      },
      (err) => {
        this.matSnackBar.open(
          'El mail ingresado fue registrado previamente',
          void 0,
          { duration: 3000 }
        )
      }
    )
  }
  newRedSocial(): RedSocial {
    return {
      nombre: '',
      url: '',
    }
  }
  addRedSocial() {
    this.user.emprendimiento.redesSociales.push(this.newRedSocial())
  }
  removeRedSocial(i: number) {
    this.user.emprendimiento.redesSociales.splice(i, 1)
  }

  onFileSelected() {
    const inputNode: any = document.getElementById('enterpriseImg')
    if (typeof FileReader !== 'undefined') {
      const reader = new FileReader()

      reader.onload = (e: any) => {
        this.user.emprendimiento.imagen = this._arrayBufferToBase64(
          e.target.result
        )
      }
      reader.readAsArrayBuffer(inputNode.files[0])
    }
  }

  _arrayBufferToBase64(buffer: ArrayBuffer) {
    var binary = ''
    var bytes = new Uint8Array(buffer)
    var len = bytes.byteLength
    for (var i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i])
    }
    return window.btoa(binary)
  }

  ngOnInit(): void {
    this.categoriesService.getCategories().subscribe((res) => {
      if (res.data.categorias) this.categorias = res.data.categorias
    })
  }

  isSelected(categoria: Categoria) {
    return this.user.emprendimiento.categorias.includes(categoria)
  }

  addCategoria(categoria: Categoria) {
    if (this.isSelected(categoria)) {
      this.user.emprendimiento.categorias =
        this.user.emprendimiento.categorias.filter(
          (cat) => cat.id != categoria.id
        )
    } else {
      this.user.emprendimiento.categorias.push(categoria)
    }
  }
  hexToRgb(hex:string) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
      r: result[1],
      g: result[2], 
      b: result[3]
    } : null
  }

  setContrast(color:string){
    var m = color.substr(1).match(color.length == 7 ? /(\S{2})/g : /(\S{1})/g);
    if (m) {
      var r = parseInt(m[0], 16), g = parseInt(m[1], 16), b = parseInt(m[2], 16)
      const brightness = Math.round((r * 299 +
        (g * 587) +
        (b * 114)) / 1000);
      return (brightness > 125) ? 'black' : 'white';
    }
    return 'black'
  }
}
