Project Name:   Multi-Threaded Matrix Multiplication

Author:         Morgan Farmer
GitProfile:     https://github.com/mFarmer2
email:          morganFarmer2475@gmail.com

Language:       Java
Environment:     Eclipse IDE; Windows

Description:
The purpose of this project is to demonstrate multithreaded programming
and concurrent practices. The program multiplies two matrices (M x K) (K x N)
to produce the third matrix (M x N). It uses K threads, each assigned a row
to multiply. 

Each matrix stores values in double Integer arrays because each object can
act as a lock to restrict access to two threads trying to access the same
value.
