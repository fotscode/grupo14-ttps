import { Component } from '@angular/core'
import { ActivatedRoute } from '@angular/router'
import { Post } from 'src/app/interfaces/Post'
import { EmprendimientosService } from 'src/app/services/emprendimientos.service'

@Component({
  selector: 'app-view-posts',
  templateUrl: './view-posts.component.html',
  styleUrls: ['./view-posts.component.css'],
})
export class ViewPostsComponent {
  constructor(
    private emprendimientoService: EmprendimientosService,
    private route: ActivatedRoute
  ) {}

  posts=[] as Post[]

  ngOnInit(): void {
    const domainUrl=this.route.snapshot.paramMap.get('domain')
    if (domainUrl) {
      this.emprendimientoService
        .getEmprendimientoByDomain(domainUrl)
        .subscribe((res) => {
          if(res.data.emprendimiento)
            this.posts=res.data.emprendimiento.posts
        })
    }
  }
}
