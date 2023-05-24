function bool=in(A,B)
% judge B is in A. if 0,B is not in A; if 1, B is in A;
sz1=size(A);
sz2=size(B);
bool=0;

if sz1(1)==0    
else
    for i=1:sz1(1)
        count=0;
        for j=1:sz2(2)
            if isequal(A{i,j},B{j})
                count=count+1;
            end
        end
        if count==sz2(2)
            bool=1;
            break;
        end
    end
end