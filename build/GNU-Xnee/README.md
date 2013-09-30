# Instructions on how testing Machine is used to build GNU Xnee

## Clients / Machines used 

### Ubuntu 12.10 (VirtualBox)

### Arm / Debian 6.0 (qemu)

### PPC / Debian 6.0 (qemu)

## Build procedure in short

* Download source code
  `cvs ...`

* Create configureation files
  `make -f Makefile.cvs`

* Configure makefiles
  `./configure`

* Build
  `make`

* Verify build
  `make check`

* Install
  `make install`






